package io.github.bhavuklabs.sleepnswipebackend.matching.domain.models;


import io.github.bhavuklabs.sleepnswipebackend.security.domain.models.User;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Table(name="swipe_quota")
@Entity
public class SwipeQuota {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column(name="daily_swipes_total")
    private int dailySwipesTotal;

    @Column(name="daily_swipes_used")
    private int dailySwipesUsed;

    @Column(name="last_reset_date")
    private LocalDate lastResetDate;

    @Column(name="bonus_swipes")
    private int bonusSwipes;


    @Column(name="updated_at")
    private LocalDateTime updatedAt;
}
