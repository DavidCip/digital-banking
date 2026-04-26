package com.bankapp.dto.response;

import java.math.BigDecimal;

public class TransferResponse {

    private String message;
    private Long senderUserId;
    private String receiverUsername;
    private BigDecimal amount;

    public TransferResponse(String message, Long senderUserId, String receiverUsername, BigDecimal amount) {
        this.message = message;
        this.senderUserId = senderUserId;
        this.receiverUsername = receiverUsername;
        this.amount = amount;
    }

    public String getMessage() {
        return message;
    }

    public Long getSenderUserId() {
        return senderUserId;
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}