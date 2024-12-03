package io.github.bhavuklabs.sleepnswipebackend.security.domain.services.implementation;

import io.github.bhavuklabs.sleepnswipebackend.security.config.providers.JwtProvider;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.entities.AuthRequestDomain;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.entities.AuthResponseDomain;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.entities.UserDomain;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.mappers.UserMapper;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.models.User;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.models.UserProfile;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.repositories.UserProfileRepository;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.repositories.UserRepository;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.services.core.UserService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImplementation extends UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtProvider jwtProvider;
    private final UserProfileRepository userProfileRepository;

    private final PasswordEncoder passwordEncoder;
    public UserServiceImplementation(UserRepository repository, UserMapper mapper, PasswordEncoder passwordEncoder, JwtProvider jwtProvider, UserProfileRepository userProfileRepository) {
        super(repository, mapper);

        this.userRepository = repository;
        this.userMapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public Optional<UserDomain> update(UserDomain userDomain) {
        return Optional.empty();
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByEmail(username)
                .stream().findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), List.of(authority));
    }

    public User registerUser(UserDomain userDomain) {
        // Create and populate User entity
        User user = this.userMapper.fromDto(userDomain);

        var savedUser = this.userRepository.findByEmail(userDomain.email()).stream().findFirst().get();
        if(savedUser != null ){
            savedUser.setPassword(passwordEncoder.encode(userDomain.password()));
            savedUser.setCreatedAt(LocalDateTime.now());
            savedUser.setUsername(userDomain.username());
            savedUser.setGender(userDomain.gender());
            savedUser.setFirstName(userDomain.firstName());
            savedUser.setLastName(userDomain.lastName());
            savedUser.setEmail(userDomain.email());
            savedUser.setDateOfBirth(userDomain.dateOfBirth());
            if (hasHealthInformation(userDomain)) {
                UserProfile userProfile = new UserProfile();
                userProfile.setProfileId(UUID.randomUUID().toString());
                userProfile.setHeightCm(userDomain.heightCm());
                userProfile.setWeightKg(userDomain.weightKg());
                userProfile.setBloodType(userDomain.bloodType());
                userProfile.setMedicalConditions(userDomain.medicalConditions());
                userProfile.setAllergies(userDomain.allergies());
                userProfile.setBmi(userDomain.bmi());
                userProfile.setCreatedAt(LocalDateTime.now());
                this.userProfileRepository.save(userProfile);
                savedUser.setUserProfile(userProfile);

                this.userRepository.save(savedUser);
            }
        }
        return savedUser;
    }

    private boolean hasHealthInformation(UserDomain userDomain) {
        return userDomain.heightCm() != null ||
                userDomain.weightKg() != null ||
                userDomain.bloodType() != null ||
                userDomain.medicalConditions() != null ||
                userDomain.allergies() != null;
    }

    @Override
    public AuthResponseDomain authenticationSignup(AuthRequestDomain requestDomain) {
        User user = new User();
        user.setEmail(requestDomain.email());
        System.out.println(requestDomain);
        user.setPassword(passwordEncoder.encode(requestDomain.password()));
        User savedUser = this.userRepository.save(user);
        String jwt = this.jwtProvider.generateToken(new UsernamePasswordAuthenticationToken(savedUser.getEmail(), null, new ArrayList<>()), savedUser.getId());
        return new AuthResponseDomain(jwt, "USer Registered");

    }

    @Override
    public AuthResponseDomain signup(UserDomain userDomain) {
        User createdUser = this.registerUser(userDomain);

        String jwt = this.jwtProvider.generateToken(new UsernamePasswordAuthenticationToken(createdUser.getEmail(), null, new ArrayList<>()), createdUser.getId());
        return new AuthResponseDomain(jwt, "User Registered");
    }

    @Override
    public AuthResponseDomain login(AuthRequestDomain authRequestDomain) {
        Authentication authentication = this.authenticate(authRequestDomain.email(), authRequestDomain.password());
        User user = this.userRepository.findByEmail(authRequestDomain.email())
                .stream().findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        String jwt = this.jwtProvider.generateToken(authentication, user.getId());

        return new AuthResponseDomain(jwt, "User Login");
    }

    private Authentication authenticate(String email, String password) {
        UserDetails userDetails = this.loadUserByUsername(email);
        System.out.println(password);
        if(!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Bad Credentials");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    public Optional<User> findUserByEmail(String email) {
        return this.userRepository.findByEmail(email).stream().findFirst();
    }

}
