package com.bankapp.service.impl;

import com.bankapp.dto.request.LostCardRequest;
import com.bankapp.dto.request.SupportTicketRequest;
import com.bankapp.dto.response.SupportTicketResponse;
import com.bankapp.entity.BankCard;
import com.bankapp.entity.SupportTicket;
import com.bankapp.entity.User;
import com.bankapp.entity.enums.CardStatus;
import com.bankapp.entity.enums.SupportTicketStatus;
import com.bankapp.entity.enums.SupportTicketType;
import com.bankapp.repository.BankCardRepository;
import com.bankapp.repository.SupportTicketRepository;
import com.bankapp.repository.UserRepository;
import com.bankapp.service.SupportService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupportServiceImpl implements SupportService {

    private final UserRepository userRepository;
    private final BankCardRepository bankCardRepository;
    private final SupportTicketRepository supportTicketRepository;

    public SupportServiceImpl(
            UserRepository userRepository,
            BankCardRepository bankCardRepository,
            SupportTicketRepository supportTicketRepository
    ) {
        this.userRepository = userRepository;
        this.bankCardRepository = bankCardRepository;
        this.supportTicketRepository = supportTicketRepository;
    }

    @Override
    public SupportTicketResponse createTicket(SupportTicketRequest request) {
        User user = getUser(request.getUserId());

        SupportTicket ticket = createAndSaveTicket(
                user,
                SupportTicketType.GENERAL,
                request.getMessage()
        );

        return mapToResponse(ticket);
    }

    @Override
    @Transactional
    public SupportTicketResponse reportLostCard(LostCardRequest request) {
        User user = getUser(request.getUserId());

        BankCard card = bankCardRepository.findById(request.getCardId())
                .orElseThrow(() -> new RuntimeException("Card not found"));

        if (!card.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("This card does not belong to this user");
        }

        card.setStatus(CardStatus.BLOCKED);
        bankCardRepository.save(card);

        SupportTicket ticket = createAndSaveTicket(
                user,
                SupportTicketType.LOST_CARD,
                "Lost card reported. Card ID " + card.getId() + " has been blocked."
        );

        return mapToResponse(ticket);
    }

    @Override
    public SupportTicketResponse forgotPin(SupportTicketRequest request) {
        User user = getUser(request.getUserId());

        SupportTicket ticket = createAndSaveTicket(
                user,
                SupportTicketType.FORGOT_PIN,
                request.getMessage()
        );

        return mapToResponse(ticket);
    }

    @Override
    public SupportTicketResponse callAssistance(SupportTicketRequest request) {
        User user = getUser(request.getUserId());

        SupportTicket ticket = createAndSaveTicket(
                user,
                SupportTicketType.CALL_ASSISTANCE,
                request.getMessage()
        );

        return mapToResponse(ticket);
    }

    @Override
    public List<SupportTicketResponse> getTicketsByUserId(Long userId) {
        return supportTicketRepository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private SupportTicket createAndSaveTicket(
            User user,
            SupportTicketType type,
            String message
    ) {
        SupportTicket ticket = new SupportTicket();
        ticket.setUser(user);
        ticket.setType(type);
        ticket.setStatus(SupportTicketStatus.OPEN);
        ticket.setMessage(message);

        return supportTicketRepository.save(ticket);
    }

    private SupportTicketResponse mapToResponse(SupportTicket ticket) {
        return new SupportTicketResponse(
                ticket.getId(),
                ticket.getUser().getId(),
                ticket.getType(),
                ticket.getStatus(),
                ticket.getMessage(),
                ticket.getCreatedAt()
        );
    }
}