package io.github.bhavuklabs.sleepnswipebackend.messaging.domain.models;


import io.github.bhavuklabs.sleepnswipebackend.matching.domain.models.UserMatch;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="chat_sessions")
public class ChatSession {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name="match_id")
    private UserMatch match;

    @Column(name="initiated_timestamp")
    private LocalDateTime initiatedTimestamp;

    @Column(name="last_message_timestamp")
    private LocalDateTime lastMessageTimestamp;
}
