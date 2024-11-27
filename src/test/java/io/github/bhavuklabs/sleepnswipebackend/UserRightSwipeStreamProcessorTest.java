package io.github.bhavuklabs.sleepnswipebackend;

import io.github.bhavuklabs.sleepnswipebackend.matching.domain.UserRightSwipeStreamProcessor;
import io.github.bhavuklabs.sleepnswipebackend.matching.domain.entities.SwipeRequestDomain;
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
        // Arrange
        UUID userId = UUID.randomUUID();
        SwipeRequestDomain swipeRequest = new SwipeRequestDomain(
                userId,
                UUID.randomUUID(),
                null // Assuming SwipeType is nullable or not required for this test
        );

        // Act
        streamProcessor.processUserRightSwipes(swipeRequest);

        // Assert
        verify(kafkaMatchProcessor).processMatchCandidates(userId);
    }

    @Test
    void testProcessUserRightSwipes_NullUserId() {
        // Arrange
        SwipeRequestDomain swipeRequest = new SwipeRequestDomain(
                null,
                UUID.randomUUID(),
                null
        );

        // Act & Assert
        // Expect this to not throw an exception, but also not call processMatchCandidates
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

        // Act
        streamProcessor.processUserRightSwipes(swipeRequest1);
        streamProcessor.processUserRightSwipes(swipeRequest2);

        // Assert
        verify(kafkaMatchProcessor).processMatchCandidates(userId1);
        verify(kafkaMatchProcessor).processMatchCandidates(userId2);
    }
}