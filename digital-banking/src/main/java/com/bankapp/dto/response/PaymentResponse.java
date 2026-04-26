package com.bankapp.dto.response;

import java.math.BigDecimal;

public class PaymentResponse {

    private String message;
    private Long userId;
    private BigDecimal paidAmount;
    private BigDecimal remainingBalance;
    private String beneficiary;
    private String type;

    public PaymentResponse(
            String message,
            Long userId,
            BigDecimal paidAmount,
            BigDecimal remainingBalance,
            String beneficiary,
            String type
    ) {
        this.message = message;
        this.userId = userId;
        this.paidAmount = paidAmount;
        this.remainingBalance = remainingBalance;
        this.beneficiary = beneficiary;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public Long getUserId() {
        return userId;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public BigDecimal getRemainingBalance() {
        return remainingBalance;
    }

    public String getBeneficiary() {
        return beneficiary;
    }

    public String getType() {
        return type;
    }
}