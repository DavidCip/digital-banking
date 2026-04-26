package com.bankapp.dto.request;

import com.bankapp.entity.enums.CurrencyCode;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class ExchangeRequest {

    @NotNull
    private Long userId;

    @NotNull
    private CurrencyCode fromCurrency;

    @NotNull
    private CurrencyCode toCurrency;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal amount;

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
}