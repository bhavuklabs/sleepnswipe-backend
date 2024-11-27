package io.github.bhavuklabs.sleepnswipebackend.matching.domain.models;


import io.github.bhavuklabs.sleepnswipebackend.security.domain.models.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="swipe_history")
public class SwipeHistory {
    public SwipeHistory(UUID id, User user, User targetProfile, LocalDateTime swipeTimestamp, SwipeType swipeType) {
        this.id = id;
        this.user = user;
        this.targetProfile = targetProfile;
        this.swipeTimestamp = swipeTimestamp;
        this.swipeType = swipeType;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="target_profile_id")
    private User targetProfile;

    @Column(name="swipe_timestamp")
    private LocalDateTime swipeTimestamp;

    @Enumerated(EnumType.STRING)
    @Column(name="swipe_type")
    private SwipeType swipeType;

    public SwipeHistory() {

    }

    public enum SwipeType {
        LEFT, RIGHT;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getTargetProfile() {
        return targetProfile;
    }

    public void setTargetProfile(User targetProfile) {
        this.targetProfile = targetProfile;
    }

    public LocalDateTime getSwipeTimestamp() {
        return swipeTimestamp;
    }

    public void setSwipeTimestamp(LocalDateTime swipeTimestamp) {
        this.swipeTimestamp = swipeTimestamp;
    }

    public SwipeType getSwipeType() {
        return swipeType;
    }

    public void setSwipeType(SwipeType swipeType) {
        this.swipeType = swipeType;
    }
}
