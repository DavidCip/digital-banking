package com.bankapp.dto.response;

import com.bankapp.entity.enums.CardStatus;

import java.time.LocalDate;

public class CardResponse {

    private Long id;
    private String cardNumber;
    private String holderName;
    private LocalDate expiryDate;
    private CardStatus status;
    private Long userId;

    public CardResponse(Long id, String cardNumber, String holderName, LocalDate expiryDate, CardStatus status, Long userId) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.holderName = holderName;
        this.expiryDate = expiryDate;
        this.status = status;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public CardStatus getStatus() {
        return status;
    }

    public Long getUserId() {
        return userId;
    }
}