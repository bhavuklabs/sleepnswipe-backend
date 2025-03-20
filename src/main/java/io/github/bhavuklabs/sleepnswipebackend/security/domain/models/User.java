package io.github.bhavuklabs.sleepnswipebackend.security.domain.models;

import io.github.bhavuklabs.sleepnswipebackend.matching.domain.models.MatchPreference;
import io.github.bhavuklabs.sleepnswipebackend.matching.domain.models.SwipeHistory;
import io.github.bhavuklabs.sleepnswipebackend.matching.domain.models.SwipeQuota;
import io.github.bhavuklabs.sleepnswipebackend.matching.domain.models.UserMatch;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Table(name="users")
@Entity
@ToString
public class User {
    public User(UUID id, String email, String password, String username, String firstName, String lastName, LocalDateTime dateOfBirth, GenderEnum gender, LocalDateTime createdAt, LocalDateTime lastLogin) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.createdAt = createdAt;
        this.lastLogin = lastLogin;
    }

    public User() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDateTime getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String email;

    @Column(nullable = true)
    private String password;

    private String username;
    private String firstName;
    private String lastName;
    private LocalDateTime dateOfBirth;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    private double latitude;
    private double longitude;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @OneToOne
    @JoinColumn(name="user_profile")
    private UserProfile userProfile;

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public SwipeQuota getSwipeQuota() {
        return swipeQuota;
    }

    public void setSwipeQuota(SwipeQuota swipeQuota) {
        this.swipeQuota = swipeQuota;
    }

    public List<SwipeHistory> getSwipeHistories() {
        return swipeHistories;
    }

    public void setSwipeHistories(List<SwipeHistory> swipeHistories) {
        this.swipeHistories = swipeHistories;
    }

    public List<MatchPreference> getMatchPreferences() {
        return matchPreferences;
    }

    public void setMatchPreferences(List<MatchPreference> matchPreferences) {
        this.matchPreferences = matchPreferences;
    }

    public List<UserMatch> getMatchesAsUser1() {
        return matchesAsUser1;
    }

    public void setMatchesAsUser1(List<UserMatch> matchesAsUser1) {
        this.matchesAsUser1 = matchesAsUser1;
    }

    public List<UserMatch> getMatchesAsUser2() {
        return matchesAsUser2;
    }

    public void setMatchesAsUser2(List<UserMatch> matchesAsUser2) {
        this.matchesAsUser2 = matchesAsUser2;
    }

    @OneToOne(mappedBy = "user")
    private SwipeQuota swipeQuota;

    @OneToMany(mappedBy = "user")
    private List<SwipeHistory> swipeHistories;

    @OneToMany(mappedBy = "user")
    private List<MatchPreference> matchPreferences;

    @OneToMany(mappedBy = "user1")
    private List<UserMatch> matchesAsUser1;

    @OneToMany(mappedBy = "user2")
    private List<UserMatch> matchesAsUser2;

    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;
}
