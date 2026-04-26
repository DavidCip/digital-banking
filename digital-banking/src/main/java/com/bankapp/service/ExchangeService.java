package com.bankapp.service;

import com.bankapp.dto.request.ExchangeRequest;
import com.bankapp.dto.response.ExchangeResponse;

import java.math.BigDecimal;
import java.util.Map;

public interface ExchangeService {

    ExchangeResponse convert(ExchangeRequest request);

    Map<String, BigDecimal> getRates();
}