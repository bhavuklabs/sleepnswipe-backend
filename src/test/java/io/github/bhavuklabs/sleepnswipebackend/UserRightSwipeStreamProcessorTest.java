package io.github.bhavuklabs.sleepnswipebackend;

import io.github.bhavuklabs.sleepnswipebackend.matching.domain.UserRightSwipeStreamProcessor;
import io.github.bhavuklabs.sleepnswipebackend.matching.domain.entities.SwipeRequestDomain;
import io.github.bhavuklabs.sleepnswipebackend.matching.domain.models.SwipeHistory;
import io.github.bhavuklabs.sleepnswipebackend.matching.domain.services.implementation.KafkaMatchProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.mockito.Mockito.verify;

public class UserRightSwipeStreamProcessorTest {

    @Mock
    private KafkaMatchProcessor kafkaMatchProcessor;

    private UserRightSwipeStreamProcessor streamProcessor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        streamProcessor = new UserRightSwipeStreamProcessor(kafkaMatchProcessor);
    }

    @Test
    void testProcessUserRightSwipes_ValidSwipeRequest() {
        UUID userId = UUID.randomUUID();
        SwipeRequestDomain swipeRequest = new SwipeRequestDomain(
                userId,
                UUID.randomUUID(),
                SwipeHistory.SwipeType.RIGHT
        );
        streamProcessor.processUserRightSwipes(swipeRequest);
        verify(kafkaMatchProcessor).processMatchCandidates(userId);
    }

    @Test
    void testProcessUserRightSwipes_NullUserId() {
        SwipeRequestDomain swipeRequest = new SwipeRequestDomain(
                null,
                UUID.randomUUID(),
                SwipeHistory.SwipeType.RIGHT
        );
        streamProcessor.processUserRightSwipes(swipeRequest);
    }

    @Test
    void testProcessUserRightSwipes_MultipleInvocations() {
        // Arrange
        UUID userId1 = UUID.randomUUID();
        UUID userId2 = UUID.randomUUID();
        SwipeRequestDomain swipeRequest1 = new SwipeRequestDomain(
                userId1,
                UUID.randomUUID(),
                null
        );
        SwipeRequestDomain swipeRequest2 = new SwipeRequestDomain(
                userId2,
                UUID.randomUUID(),
                null
        );
        streamProcessor.processUserRightSwipes(swipeRequest1);
        streamProcessor.processUserRightSwipes(swipeRequest2);

        verify(kafkaMatchProcessor).processMatchCandidates(userId1);
        verify(kafkaMatchProcessor).processMatchCandidates(userId2);
    }
}