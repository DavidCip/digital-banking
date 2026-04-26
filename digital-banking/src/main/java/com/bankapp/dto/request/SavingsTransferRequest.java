package com.bankapp.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class SavingsTransferRequest {

    @NotNull
    private Long userId;

    @NotNull
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;

    public Long getUserId() {
        return userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}