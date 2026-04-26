package com.bankapp.service.impl;

import com.bankapp.dto.request.LoginRequest;
import com.bankapp.dto.request.RegisterRequest;
import com.bankapp.dto.response.LoginResponse;
import com.bankapp.dto.response.RegisterResponse;
import com.bankapp.entity.BankAccount;
import com.bankapp.entity.BankCard;
import com.bankapp.entity.User;
import com.bankapp.entity.enums.CardStatus;
import com.bankapp.repository.BankAccountRepository;
import com.bankapp.repository.BankCardRepository;
import com.bankapp.repository.UserRepository;
import com.bankapp.service.AuthService;
import com.bankapp.util.CardGenerator;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;
    private final BankCardRepository bankCardRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(
            UserRepository userRepository,
            BankAccountRepository bankAccountRepository,
            BankCardRepository bankCardRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.bankCardRepository = bankCardRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public RegisterResponse register(RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByCnp(request.getCnp())) {
            throw new RuntimeException("CNP already exists");
        }

        if (userRepository.existsByPhone(request.getPhone())) {
            throw new RuntimeException("Phone already exists");
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .cnp(request.getCnp())
                .phone(request.getPhone())
                .active(true)
                .build();

        User savedUser = userRepository.save(user);

        BankAccount bankAccount = BankAccount.builder()
                .user(savedUser)
                .build();

        bankAccountRepository.save(bankAccount);

        String cardNumber = generateUniqueCardNumber();

        BankCard card = BankCard.builder()
                .user(savedUser)
                .cardNumber(cardNumber)
                .holderName(savedUser.getFirstName() + " " + savedUser.getLastName())
                .expiryDate(LocalDate.now().plusYears(5))
                .status(CardStatus.ACTIVE)
                .build();

        bankCardRepository.save(card);

        return new RegisterResponse(
                "User registered successfully",
                savedUser.getId(),
                savedUser.getUsername(),
                cardNumber
        );
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        if (!user.isActive()) {
            throw new RuntimeException("Account is disabled");
        }

        return new LoginResponse(
                "Login successful",
                user.getId(),
                user.getUsername()
        );
    }

    private String generateUniqueCardNumber() {
        String cardNumber;

        do {
            cardNumber = CardGenerator.generateCardNumber();
        } while (bankCardRepository.existsByCardNumber(cardNumber));

        return cardNumber;
    }
}