package com.bankapp.service;

import com.bankapp.dto.request.DepositRequest;
import com.bankapp.dto.request.SavingsTransferRequest;
import com.bankapp.dto.response.AccountResponse;

public interface AccountService {

    AccountResponse getAccountByUserId(Long userId);
    AccountResponse deposit(DepositRequest request);
    AccountResponse transferToSavings(SavingsTransferRequest request);
    AccountResponse transferFromSavings(SavingsTransferRequest request);
}