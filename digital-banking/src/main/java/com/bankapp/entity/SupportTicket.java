package com.bankapp.entity;

import com.bankapp.entity.enums.SupportTicketStatus;
import com.bankapp.entity.enums.SupportTicketType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "support_tickets")
public class SupportTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private SupportTicketType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private SupportTicketStatus status;

    @Column(nullable = false)
    private String message;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();

        if (status == null) {
            status = SupportTicketStatus.OPEN;
        }
    }

    public Long getId() {
        return id;
    }

    public SupportTicketType getType() {
        return type;
    }

    public void setType(SupportTicketType type) {
        this.type = type;
    }

    public SupportTicketStatus getStatus() {
        return status;
    }

    public void setStatus(SupportTicketStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}