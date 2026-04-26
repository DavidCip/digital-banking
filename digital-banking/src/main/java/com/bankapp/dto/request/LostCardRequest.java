package com.bankapp.dto.request;

import jakarta.validation.constraints.NotNull;

public class LostCardRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Long cardId;

    public Long getUserId() {
        return userId;
    }

    public Long getCardId() {
        return cardId;
    }
}