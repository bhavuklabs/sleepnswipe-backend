package io.github.bhavuklabs.sleepnswipebackend.matching.domain.entities;

import io.github.bhavuklabs.sleepnswipebackend.matching.domain.models.SwipeHistory;

import java.util.UUID;

public record SwipeRequestDomain(
        UUID userId,
        UUID targetProfileId,
        SwipeHistory.SwipeType swipeType
) {
}
