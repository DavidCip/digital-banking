package com.bankapp.dto.response;

import java.math.BigDecimal;

public class AccountResponse {

    private Long accountId;
    private Long userId;
    private BigDecimal activeBalance;
    private BigDecimal savingsBalance;
    private String currency;

    public AccountResponse(Long accountId, Long userId, BigDecimal activeBalance, BigDecimal savingsBalance, String currency) {
        this.accountId = accountId;
        this.userId = userId;
        this.activeBalance = activeBalance;
        this.savingsBalance = savingsBalance;
        this.currency = currency;
    }

    public Long getAccountId() {
        return accountId;
    }

    public Long getUserId() {
        return userId;
    }

    public BigDecimal getActiveBalance() {
        return activeBalance;
    }

    public BigDecimal getSavingsBalance() {
        return savingsBalance;
    }

    public String getCurrency() {
        return currency;
    }
}