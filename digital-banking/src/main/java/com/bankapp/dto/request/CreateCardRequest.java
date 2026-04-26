package com.bankapp.dto.request;

import jakarta.validation.constraints.NotNull;

public class CreateCardRequest {

    @NotNull
    private Long userId;

    public Long getUserId() {
        return userId;
    }
}