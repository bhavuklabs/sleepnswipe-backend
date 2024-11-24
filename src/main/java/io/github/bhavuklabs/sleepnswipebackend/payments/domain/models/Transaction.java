package io.github.bhavuklabs.sleepnswipebackend.payments.domain.models;

import io.github.bhavuklabs.sleepnswipebackend.security.domain.models.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column(precision = 10)
    private double amount;

    @Column(name="transaction_type")
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column(name="payment_method")
    private String paymentMethod;

    @Column(name="transaction_timestamp")
    private LocalDateTime transactionTimestamp;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    public enum TransactionType {
        SWIPE_PURCHASE, SUBSCRIPTION, BONUS;
    }

    public enum TransactionStatus {
        SUCCESS, PENDING, FAILED;
    }
}
