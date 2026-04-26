package com.bankapp.service.impl;

import com.bankapp.dto.request.DepositRequest;
import com.bankapp.dto.request.SavingsTransferRequest;
import com.bankapp.dto.response.AccountResponse;
import com.bankapp.entity.BankAccount;
import com.bankapp.entity.Transaction;
import com.bankapp.entity.enums.TransactionStatus;
import com.bankapp.entity.enums.TransactionType;
import com.bankapp.repository.BankAccountRepository;
import com.bankapp.repository.TransactionRepository;
import com.bankapp.service.AccountService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    private final BankAccountRepository bankAccountRepository;
    private final TransactionRepository transactionRepository;

    public AccountServiceImpl(
            BankAccountRepository bankAccountRepository,
            TransactionRepository transactionRepository
    ) {
        this.bankAccountRepository = bankAccountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public AccountResponse getAccountByUserId(Long userId) {

        BankAccount account = bankAccountRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Bank account not found"));

        return mapToResponse(account);
    }

    @Override
    @Transactional
    public AccountResponse deposit(DepositRequest request) {

        BankAccount account = bankAccountRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Bank account not found"));

        account.setActiveBalance(account.getActiveBalance().add(request.getAmount()));

        BankAccount savedAccount = bankAccountRepository.save(account);

        transactionRepository.save(Transaction.builder()
                .user(savedAccount.getUser())
                .type(TransactionType.DEPOSIT)
                .status(TransactionStatus.SUCCESS)
                .amount(request.getAmount())
                .currency(savedAccount.getCurrency())
                .description("Money deposited to active balance")
                .build());

        return mapToResponse(savedAccount);
    }

    @Override
    @Transactional
    public AccountResponse transferToSavings(SavingsTransferRequest request) {

        BankAccount account = bankAccountRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Bank account not found"));

        if (account.getActiveBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient active balance");
        }

        account.setActiveBalance(account.getActiveBalance().subtract(request.getAmount()));
        account.setSavingsBalance(account.getSavingsBalance().add(request.getAmount()));

        BankAccount savedAccount = bankAccountRepository.save(account);

        transactionRepository.save(Transaction.builder()
                .user(savedAccount.getUser())
                .type(TransactionType.SAVINGS_DEPOSIT)
                .status(TransactionStatus.SUCCESS)
                .amount(request.getAmount())
                .currency(savedAccount.getCurrency())
                .description("Money transferred from active balance to savings")
                .build());

        return mapToResponse(savedAccount);
    }

    @Override
    @Transactional
    public AccountResponse transferFromSavings(SavingsTransferRequest request) {

        BankAccount account = bankAccountRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Bank account not found"));

        if (account.getSavingsBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient savings balance");
        }

        account.setSavingsBalance(account.getSavingsBalance().subtract(request.getAmount()));
        account.setActiveBalance(account.getActiveBalance().add(request.getAmount()));

        BankAccount savedAccount = bankAccountRepository.save(account);

        transactionRepository.save(Transaction.builder()
                .user(savedAccount.getUser())
                .type(TransactionType.SAVINGS_WITHDRAW)
                .status(TransactionStatus.SUCCESS)
                .amount(request.getAmount())
                .currency(savedAccount.getCurrency())
                .description("Money transferred from savings to active balance")
                .build());

        return mapToResponse(savedAccount);
    }

    private AccountResponse mapToResponse(BankAccount account) {
        return new AccountResponse(
                account.getId(),
                account.getUser().getId(),
                account.getActiveBalance(),
                account.getSavingsBalance(),
                account.getCurrency()
        );
    }
}