package com.bankapp.dto.response;

import java.math.BigDecimal;

public class TransferResponse {

    private String message;
    private Long senderUserId;
    private String receiverFullName;
    private BigDecimal amount;

    public TransferResponse(
            String message,
            Long senderUserId,
            String receiverFullName,
            BigDecimal amount
    ) {
        this.message = message;
        this.senderUserId = senderUserId;
        this.receiverFullName = receiverFullName;
        this.amount = amount;
    }

    public String getMessage() {
        return message;
    }

    public Long getSenderUserId() {
        return senderUserId;
    }

    public String getReceiverFullName() {
        return receiverFullName;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}