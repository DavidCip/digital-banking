package com.bankapp.service;

import com.bankapp.dto.request.CreateCardRequest;
import com.bankapp.dto.response.CardResponse;

import java.util.List;

public interface CardService {

    List<CardResponse> getCardsByUserId(Long userId);

    CardResponse createCard(CreateCardRequest request);

    CardResponse blockCard(Long cardId);
}