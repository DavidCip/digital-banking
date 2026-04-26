package com.bankapp.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class UserTransferRequest {

    @NotNull
    private Long senderUserId;

    @NotBlank
    private String receiverUsername;

    @NotNull
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;

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