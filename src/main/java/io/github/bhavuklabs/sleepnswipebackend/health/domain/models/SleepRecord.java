package io.github.bhavuklabs.sleepnswipebackend.health.domain.models;


import io.github.bhavuklabs.sleepnswipebackend.security.domain.models.User;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="sleep_data")
public class SleepRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column(name="device_id")
    private UUID deviceId;

    @Column(name="record_date")
    private LocalDate recordDate;

    @Column(name="total_sleep_duration_minutes")
    private int totalSleepDurationMinutes;

    @Column(name="deep_sleep_minutes")
    private int deepSleepMinutes;

    @Column(name="light_sleep_minutes")
    private int lightSleepMinutes;

    @Column(name="rem_sleep_minutes")
    private int remSleepMinutes;

    @Column(name="sleep_efficiency_percentage", precision = 5)
    private double sleepEfficiencyPercentage;

    @Column(name="sleep_quality_score")
    private int sleepQualityScore;

    @Column(name="bedtime")
    private LocalDateTime bedtime;

    @Column(name="wake_time")
    private LocalDateTime wakeTime;

    @Column(name="heart_rate_during_sleep", precision=5)
    private double heartRateDuringSleep;

    @Enumerated(EnumType.STRING)
    @Column(name="device_vendor")
    private DeviceVendor deviceVendor;

    public static enum DeviceVendor {
        NOISE, BOAT, HAMMER, OTHERS;
    }

    @Column(name="created_at")
    private LocalDateTime createdAt;

}
