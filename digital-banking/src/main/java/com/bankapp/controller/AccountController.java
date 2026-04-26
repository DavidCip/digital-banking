package com.bankapp.controller;

import com.bankapp.config.ApiPaths;
import com.bankapp.dto.request.DepositRequest;
import com.bankapp.dto.request.SavingsTransferRequest;
import com.bankapp.dto.response.AccountResponse;
import com.bankapp.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiPaths.ACCOUNTS)
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(ApiPaths.USER_BY_ID)
    public ResponseEntity<AccountResponse> getAccountByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(accountService.getAccountByUserId(userId));
    }

    @PostMapping(ApiPaths.DEPOSIT)
    public ResponseEntity<AccountResponse> deposit(@Valid @RequestBody DepositRequest request) {
        return ResponseEntity.ok(accountService.deposit(request));
    }

    @PostMapping(ApiPaths.TRANSFER_TO_SAVINGS)
    public ResponseEntity<AccountResponse> transferToSavings(@Valid @RequestBody SavingsTransferRequest request) {
        return ResponseEntity.ok(accountService.transferToSavings(request));
    }

    @PostMapping(ApiPaths.TRANSFER_FROM_SAVINGS)
    public ResponseEntity<AccountResponse> transferFromSavings(@Valid @RequestBody SavingsTransferRequest request) {
        return ResponseEntity.ok(accountService.transferFromSavings(request));
    }
}