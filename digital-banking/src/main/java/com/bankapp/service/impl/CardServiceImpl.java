package com.bankapp.service.impl;

import com.bankapp.dto.request.CreateCardRequest;
import com.bankapp.dto.response.CardResponse;
import com.bankapp.entity.BankCard;
import com.bankapp.entity.User;
import com.bankapp.entity.enums.CardStatus;
import com.bankapp.repository.BankCardRepository;
import com.bankapp.repository.UserRepository;
import com.bankapp.service.CardService;
import com.bankapp.util.CardGenerator;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CardServiceImpl implements CardService {

    private final BankCardRepository bankCardRepository;
    private final UserRepository userRepository;

    public CardServiceImpl(BankCardRepository bankCardRepository, UserRepository userRepository) {
        this.bankCardRepository = bankCardRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<CardResponse> getCardsByUserId(Long userId) {
        return bankCardRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public CardResponse createCard(CreateCardRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String cardNumber = generateUniqueCardNumber();

        BankCard card = new BankCard();
        card.setUser(user);
        card.setCardNumber(cardNumber);
        card.setHolderName(user.getFirstName() + " " + user.getLastName());
        card.setExpiryDate(LocalDate.now().plusYears(5));
        card.setStatus(CardStatus.ACTIVE);

        BankCard savedCard = bankCardRepository.save(card);

        return mapToResponse(savedCard);
    }

    @Override
    public CardResponse blockCard(Long cardId) {

        BankCard card = bankCardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        card.setStatus(CardStatus.BLOCKED);

        BankCard savedCard = bankCardRepository.save(card);

        return mapToResponse(savedCard);
    }

    private String generateUniqueCardNumber() {
        String cardNumber;

        do {
            cardNumber = CardGenerator.generateCardNumber();
        } while (bankCardRepository.existsByCardNumber(cardNumber));

        return cardNumber;
    }

    private CardResponse mapToResponse(BankCard card) {
        return new CardResponse(
                card.getId(),
                card.getCardNumber(),
                card.getHolderName(),
                card.getExpiryDate(),
                card.getStatus(),
                card.getUser().getId()
        );
    }
}