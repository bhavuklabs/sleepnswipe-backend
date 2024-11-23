package io.github.bhavuklabs.sleepnswipebackend.security.domain.services.implementation;

import io.github.bhavuklabs.sleepnswipebackend.security.config.providers.JwtProvider;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.entities.AuthRequestDomain;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.entities.AuthResponseDomain;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.entities.UserDomain;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.mappers.UserMapper;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.models.User;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.models.UserProfile;
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

    private final PasswordEncoder passwordEncoder;
    public UserServiceImplementation(UserRepository repository, UserMapper mapper, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        super(repository, mapper);

        this.userRepository = repository;
        this.userMapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        User savedUser = this.userRepository.save(user);

        if (hasHealthInformation(userDomain)) {
            UserProfile userProfile = new UserProfile();
            userProfile.setProfileId(UUID.randomUUID().toString());
            userProfile.setUserId(savedUser.getId().toString());
            userProfile.setHeightCm(userDomain.heightCm());
            userProfile.setWeightKg(userDomain.weightKg());
            userProfile.setBloodType(userDomain.bloodType());
            userProfile.setMedicalConditions(userDomain.medicalConditions());
            userProfile.setAllergies(userDomain.allergies());
            userProfile.setBmi(userDomain.bmi());
            userProfile.setCreatedAt(LocalDateTime.now());
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
        if(!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Bad Credentials");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}
