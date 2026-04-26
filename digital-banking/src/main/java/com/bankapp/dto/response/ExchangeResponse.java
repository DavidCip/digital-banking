package com.bankapp.dto.response;

import com.bankapp.entity.enums.CurrencyCode;

import java.math.BigDecimal;

public class ExchangeResponse {

    private String message;
    private Long userId;
    private CurrencyCode fromCurrency;
    private CurrencyCode toCurrency;
    private BigDecimal amount;
    private BigDecimal convertedAmount;
    private BigDecimal remainingRonBalance;

    public ExchangeResponse(
            String message,
            Long userId,
            CurrencyCode fromCurrency,
            CurrencyCode toCurrency,
            BigDecimal amount,
            BigDecimal convertedAmount,
            BigDecimal remainingRonBalance
    ) {
        this.message = message;
        this.userId = userId;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.amount = amount;
        this.convertedAmount = convertedAmount;
        this.remainingRonBalance = remainingRonBalance;
    }

    public String getMessage() {
        return message;
    }

    public Long getUserId() {
        return userId;
    }

    public CurrencyCode getFromCurrency() {
        return fromCurrency;
    }

    public CurrencyCode getToCurrency() {
        return toCurrency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getConvertedAmount() {
        return convertedAmount;
    }

    public BigDecimal getRemainingRonBalance() {
        return remainingRonBalance;
    }
}