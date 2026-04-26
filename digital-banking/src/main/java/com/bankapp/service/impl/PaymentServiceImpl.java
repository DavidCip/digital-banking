package com.bankapp.service.impl;

import com.bankapp.dto.request.PaymentRequest;
import com.bankapp.dto.response.PaymentResponse;
import com.bankapp.entity.BankAccount;
import com.bankapp.entity.Transaction;
import com.bankapp.entity.User;
import com.bankapp.entity.enums.TransactionStatus;
import com.bankapp.entity.enums.TransactionType;
import com.bankapp.repository.BankAccountRepository;
import com.bankapp.repository.TransactionRepository;
import com.bankapp.repository.UserRepository;
import com.bankapp.service.PaymentService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;
    private final TransactionRepository transactionRepository;

    public PaymentServiceImpl(
            UserRepository userRepository,
            BankAccountRepository bankAccountRepository,
            TransactionRepository transactionRepository
    ) {
        this.userRepository = userRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    @Transactional
    public PaymentResponse payBill(PaymentRequest request) {
        return processPayment(request, TransactionType.BILL_PAYMENT, "Bill payment successful");
    }

    @Override
    @Transactional
    public PaymentResponse payFine(PaymentRequest request) {
        return processPayment(request, TransactionType.FINE_PAYMENT, "Fine payment successful");
    }

    private PaymentResponse processPayment(
            PaymentRequest request,
            TransactionType transactionType,
            String successMessage
    ) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        BankAccount account = bankAccountRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Bank account not found"));

        if (account.getActiveBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        account.setActiveBalance(account.getActiveBalance().subtract(request.getAmount()));

        BankAccount savedAccount = bankAccountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setType(transactionType);
        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setAmount(request.getAmount());
        transaction.setCurrency(savedAccount.getCurrency());
        transaction.setDescription(
                request.getDescription() != null && !request.getDescription().isBlank()
                        ? request.getDescription()
                        : transactionType + " to " + request.getBeneficiary()
        );

        transactionRepository.save(transaction);

        return new PaymentResponse(
                successMessage,
                user.getId(),
                request.getAmount(),
                savedAccount.getActiveBalance(),
                request.getBeneficiary(),
                transactionType.name()
        );
    }
}