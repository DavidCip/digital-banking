package com.bankapp.dto.response;

import com.bankapp.entity.enums.SupportTicketStatus;
import com.bankapp.entity.enums.SupportTicketType;

import java.time.LocalDateTime;

public class SupportTicketResponse {

    private Long id;
    private Long userId;
    private SupportTicketType type;
    private SupportTicketStatus status;
    private String message;
    private LocalDateTime createdAt;

    public SupportTicketResponse(
            Long id,
            Long userId,
            SupportTicketType type,
            SupportTicketStatus status,
            String message,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.status = status;
        this.message = message;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public SupportTicketType getType() {
        return type;
    }

    public SupportTicketStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}