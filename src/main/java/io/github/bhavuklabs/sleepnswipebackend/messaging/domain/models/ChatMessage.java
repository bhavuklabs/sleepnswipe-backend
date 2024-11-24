package io.github.bhavuklabs.sleepnswipebackend.messaging.domain.models;

import io.github.bhavuklabs.sleepnswipebackend.security.domain.models.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="chat_messages")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name="session_id")
    private ChatSession chatSessions;

    @ManyToOne
    @JoinColumn(name="sender_id")
    private User user;

    @Column(name="message_text")
    private String messageText;

    @Column(name="timestamp")
    private LocalDateTime timestamp;

    @Column(name="is_read")
    public boolean isRead;
}
