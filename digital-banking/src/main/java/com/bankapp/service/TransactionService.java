package com.bankapp.service;

import com.bankapp.dto.response.TransactionResponse;

import java.util.List;

public interface TransactionService {

    List<TransactionResponse> getTransactionsByUserId(Long userId);
}