package io.github.bhavuklabs.sleepnswipebackend.matching.domain.entities;

public record SwipeHistoryDomain(
        String email,
        String swipeType,
        String targetProfileEmail
) {
}
