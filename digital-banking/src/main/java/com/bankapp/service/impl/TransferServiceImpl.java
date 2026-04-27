package com.bankapp.service.impl;

import com.bankapp.dto.request.TransferRequest;
import com.bankapp.dto.response.TransferResponse;
import com.bankapp.entity.BankAccount;
import com.bankapp.entity.Transaction;
import com.bankapp.entity.User;
import com.bankapp.entity.enums.TransactionStatus;
import com.bankapp.entity.enums.TransactionType;
import com.bankapp.repository.BankAccountRepository;
import com.bankapp.repository.TransactionRepository;
import com.bankapp.repository.UserRepository;
import com.bankapp.service.TransferService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class TransferServiceImpl implements TransferService {

    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;
    private final TransactionRepository transactionRepository;

    public TransferServiceImpl(
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
    public TransferResponse transferToUser(TransferRequest request) {

        User sender = userRepository.findById(request.getSenderUserId())
                .orElseThrow(() -> new RuntimeException("Sender user not found"));

        User receiver = userRepository
                .findByFirstNameAndLastName(
                        request.getFirstName(),
                        request.getLastName()
                )
                .orElseThrow(() ->
                        new RuntimeException("Recipient not found"));

        if (sender.getId().equals(receiver.getId())) {
            throw new RuntimeException("You cannot transfer money to yourself");
        }

        BankAccount senderAccount = bankAccountRepository.findByUserId(sender.getId())
                .orElseThrow(() -> new RuntimeException("Sender bank account not found"));

        BankAccount receiverAccount = bankAccountRepository.findByUserId(receiver.getId())
                .orElseThrow(() -> new RuntimeException("Receiver bank account not found"));

        if (senderAccount.getActiveBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        senderAccount.setActiveBalance(senderAccount.getActiveBalance().subtract(request.getAmount()));
        receiverAccount.setActiveBalance(receiverAccount.getActiveBalance().add(request.getAmount()));

        bankAccountRepository.save(senderAccount);
        bankAccountRepository.save(receiverAccount);

        transactionRepository.save(Transaction.builder()
                .user(sender)
                .targetUser(receiver)
                .type(TransactionType.TRANSFER_SENT)
                .status(TransactionStatus.SUCCESS)
                .amount(request.getAmount())
                .currency(senderAccount.getCurrency())
                .description("Money sent to " + receiver.getUsername())
                .build());

        transactionRepository.save(Transaction.builder()
                .user(receiver)
                .targetUser(sender)
                .type(TransactionType.TRANSFER_RECEIVED)
                .status(TransactionStatus.SUCCESS)
                .amount(request.getAmount())
                .currency(receiverAccount.getCurrency())
                .description("Money received from " + sender.getUsername())
                .build());

        return new TransferResponse(
                "Transfer successful",
                sender.getId(),
                receiver.getUsername(),
                request.getAmount()
        );
    }
}