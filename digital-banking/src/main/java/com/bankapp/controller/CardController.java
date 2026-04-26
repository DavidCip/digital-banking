package com.bankapp.controller;

import com.bankapp.config.ApiPaths;
import com.bankapp.dto.request.CreateCardRequest;
import com.bankapp.dto.response.CardResponse;
import com.bankapp.service.CardService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiPaths.CARDS)
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping(ApiPaths.USER_BY_ID)
    public ResponseEntity<List<CardResponse>> getCardsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(cardService.getCardsByUserId(userId));
    }

    @PostMapping(ApiPaths.CREATE)
    public ResponseEntity<CardResponse> createCard(@Valid @RequestBody CreateCardRequest request) {
        return ResponseEntity.ok(cardService.createCard(request));
    }

    @PatchMapping(ApiPaths.BLOCK_CARD)
    public ResponseEntity<CardResponse> blockCard(@PathVariable Long cardId) {
        return ResponseEntity.ok(cardService.blockCard(cardId));
    }
}