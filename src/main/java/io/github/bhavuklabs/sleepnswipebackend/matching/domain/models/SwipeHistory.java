package io.github.bhavuklabs.sleepnswipebackend.matching.domain.models;


import io.github.bhavuklabs.sleepnswipebackend.security.domain.models.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="swipe_history")
public class SwipeHistory {

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

    public enum SwipeType {
        LEFT, RIGHT;
    }
}
