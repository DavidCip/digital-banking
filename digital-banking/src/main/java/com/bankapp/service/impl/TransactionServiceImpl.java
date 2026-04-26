package com.bankapp.service.impl;

import com.bankapp.dto.response.TransactionResponse;
import com.bankapp.entity.Transaction;
import com.bankapp.repository.TransactionRepository;
import com.bankapp.service.TransactionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<TransactionResponse> getTransactionsByUserId(Long userId) {
        return transactionRepository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private TransactionResponse mapToResponse(Transaction transaction) {
        return new TransactionResponse(
                transaction.getId(),
                transaction.getType(),
                transaction.getStatus(),
                transaction.getAmount(),
                transaction.getCurrency(),
                transaction.getDescription(),
                transaction.getUser().getId(),
                transaction.getTargetUser() != null ? transaction.getTargetUser().getId() : null,
                transaction.getCreatedAt()
        );
    }
}