package com.bankapp.controller;

import com.bankapp.config.ApiPaths;
import com.bankapp.dto.request.PaymentRequest;
import com.bankapp.dto.response.PaymentResponse;
import com.bankapp.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiPaths.PAYMENTS)
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping(ApiPaths.BILL)
    public ResponseEntity<PaymentResponse> payBill(@Valid @RequestBody PaymentRequest request) {
        return ResponseEntity.ok(paymentService.payBill(request));
    }

    @PostMapping(ApiPaths.FINE)
    public ResponseEntity<PaymentResponse> payFine(@Valid @RequestBody PaymentRequest request) {
        return ResponseEntity.ok(paymentService.payFine(request));
    }
}