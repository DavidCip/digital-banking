package com.bankapp.service.impl;

import com.bankapp.dto.request.ExchangeRequest;
import com.bankapp.dto.response.ExchangeResponse;
import com.bankapp.entity.BankAccount;
import com.bankapp.entity.Transaction;
import com.bankapp.entity.User;
import com.bankapp.entity.enums.CurrencyCode;
import com.bankapp.entity.enums.TransactionStatus;
import com.bankapp.entity.enums.TransactionType;
import com.bankapp.repository.BankAccountRepository;
import com.bankapp.repository.TransactionRepository;
import com.bankapp.repository.UserRepository;
import com.bankapp.service.ExchangeService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Service
public class ExchangeServiceImpl implements ExchangeService {

    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;
    private final TransactionRepository transactionRepository;

    private static final Map<CurrencyCode, BigDecimal> RATES_TO_RON = Map.of(
            CurrencyCode.RON, BigDecimal.ONE,
            CurrencyCode.EUR, new BigDecimal("4.97"),
            CurrencyCode.USD, new BigDecimal("4.60"),
            CurrencyCode.RUB, new BigDecimal("0.05"),
            CurrencyCode.JPY, new BigDecimal("0.031")
    );

    public ExchangeServiceImpl(
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
    public ExchangeResponse convert(ExchangeRequest request) {

        if (request.getFromCurrency() == request.getToCurrency()) {
            throw new RuntimeException("Currencies must be different");
        }

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        BankAccount account = bankAccountRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Bank account not found"));

        BigDecimal amountInRon = request.getAmount()
                .multiply(RATES_TO_RON.get(request.getFromCurrency()))
                .setScale(2, RoundingMode.HALF_UP);

        if (account.getActiveBalance().compareTo(amountInRon) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        BigDecimal convertedAmount = amountInRon
                .divide(RATES_TO_RON.get(request.getToCurrency()), 2, RoundingMode.HALF_UP);

        account.setActiveBalance(account.getActiveBalance().subtract(amountInRon));

        BankAccount savedAccount = bankAccountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setType(TransactionType.CURRENCY_EXCHANGE);
        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setAmount(amountInRon);
        transaction.setCurrency("RON");
        transaction.setDescription(
                "Converted " + request.getAmount() + " " + request.getFromCurrency()
                        + " to " + convertedAmount + " " + request.getToCurrency()
        );

        transactionRepository.save(transaction);

        return new ExchangeResponse(
                "Currency exchange successful",
                user.getId(),
                request.getFromCurrency(),
                request.getToCurrency(),
                request.getAmount(),
                convertedAmount,
                savedAccount.getActiveBalance()
        );
    }

    @Override
    public Map<String, BigDecimal> getRates() {
        return Map.of(
                "RON", RATES_TO_RON.get(CurrencyCode.RON),
                "EUR", RATES_TO_RON.get(CurrencyCode.EUR),
                "USD", RATES_TO_RON.get(CurrencyCode.USD),
                "RUB", RATES_TO_RON.get(CurrencyCode.RUB),
                "JPY", RATES_TO_RON.get(CurrencyCode.JPY)
        );
    }
}