package com.bankapp.dto.response;

import com.bankapp.entity.enums.TransactionStatus;
import com.bankapp.entity.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionResponse {

    private Long id;
    private TransactionType type;
    private TransactionStatus status;
    private BigDecimal amount;
    private String currency;
    private String description;
    private Long userId;
    private Long targetUserId;
    private LocalDateTime createdAt;

    public TransactionResponse(
            Long id,
            TransactionType type,
            TransactionStatus status,
            BigDecimal amount,
            String currency,
            String description,
            Long userId,
            Long targetUserId,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.type = type;
        this.status = status;
        this.amount = amount;
        this.currency = currency;
        this.description = description;
        this.userId = userId;
        this.targetUserId = targetUserId;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getDescription() {
        return description;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getTargetUserId() {
        return targetUserId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}