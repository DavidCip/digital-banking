package com.bankapp.controller;

import com.bankapp.config.ApiPaths;
import com.bankapp.dto.request.ExchangeRequest;
import com.bankapp.dto.response.ExchangeResponse;
import com.bankapp.service.ExchangeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping(ApiPaths.EXCHANGE)
public class ExchangeController {

    private final ExchangeService exchangeService;

    public ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @PostMapping(ApiPaths.CONVERT)
    public ResponseEntity<ExchangeResponse> convert(@Valid @RequestBody ExchangeRequest request) {
        return ResponseEntity.ok(exchangeService.convert(request));
    }

    @GetMapping(ApiPaths.RATES)
    public ResponseEntity<Map<String, BigDecimal>> getRates() {
        return ResponseEntity.ok(exchangeService.getRates());
    }
}