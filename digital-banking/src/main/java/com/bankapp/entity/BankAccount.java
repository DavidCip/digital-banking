package com.bankapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bank_accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal activeBalance;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal savingsBalance;

    @Column(nullable = false, length = 3)
    private String currency;

    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();

        if (activeBalance == null) {
            activeBalance = BigDecimal.ZERO;
        }

        if (savingsBalance == null) {
            savingsBalance = BigDecimal.ZERO;
        }

        if (currency == null) {
            currency = "RON";
        }
    }
}
