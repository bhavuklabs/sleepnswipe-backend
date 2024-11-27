package io.github.bhavuklabs.sleepnswipebackend.matching.domain.entities;

public record SwipeQuotaDomain(
        String email,
        int dailySwipesTotal,
        int dailySwipesUsed,
        int bonusSwipes
) {
}
