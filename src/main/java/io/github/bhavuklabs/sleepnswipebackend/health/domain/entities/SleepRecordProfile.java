package io.github.bhavuklabs.sleepnswipebackend.health.domain.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record SleepRecordProfile(
        String email,
        String deviceId,
        LocalDate recordDate,
        int totalSleepDurationMinutes,
        int deepSleepMinutes,
        int lightSleepMinutes,
        int remSleepMinutes,
        double sleepEfficiencyPercentage,
        int sleepQualityScore,
        LocalDateTime bedtime,
        LocalDateTime wakeTime,
        double heartRateDuringSleep,
        String deviceVendor
) {
}
