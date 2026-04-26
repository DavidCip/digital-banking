package com.bankapp.controller;

import com.bankapp.config.ApiPaths;
import com.bankapp.dto.response.TransactionResponse;
import com.bankapp.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiPaths.TRANSACTIONS)
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping(ApiPaths.USER_BY_ID)
    public ResponseEntity<List<TransactionResponse>> getTransactionsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(transactionService.getTransactionsByUserId(userId));
    }
}