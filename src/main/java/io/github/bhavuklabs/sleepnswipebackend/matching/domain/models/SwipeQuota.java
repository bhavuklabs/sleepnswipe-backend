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

    public SwipeQuota(UUID id, User user, int dailySwipesTotal, int dailySwipesUsed, LocalDate lastResetDate, int bonusSwipes, LocalDateTime updatedAt) {
        this.id = id;
        this.user = user;
        this.dailySwipesTotal = dailySwipesTotal;
        this.dailySwipesUsed = dailySwipesUsed;
        this.lastResetDate = lastResetDate;
        this.bonusSwipes = bonusSwipes;
        this.updatedAt = updatedAt;
    }

    public SwipeQuota() {

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

    public int getDailySwipesTotal() {
        return dailySwipesTotal;
    }

    public void setDailySwipesTotal(int dailySwipesTotal) {
        this.dailySwipesTotal = dailySwipesTotal;
    }

    public int getDailySwipesUsed() {
        return dailySwipesUsed;
    }

    public void setDailySwipesUsed(int dailySwipesUsed) {
        this.dailySwipesUsed = dailySwipesUsed;
    }

    public LocalDate getLastResetDate() {
        return lastResetDate;
    }

    public void setLastResetDate(LocalDate lastResetDate) {
        this.lastResetDate = lastResetDate;
    }

    public int getBonusSwipes() {
        return bonusSwipes;
    }

    public void setBonusSwipes(int bonusSwipes) {
        this.bonusSwipes = bonusSwipes;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
