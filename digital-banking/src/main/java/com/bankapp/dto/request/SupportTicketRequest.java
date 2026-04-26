package com.bankapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SupportTicketRequest {

    @NotNull
    private Long userId;

    @NotBlank
    private String message;

    public Long getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }
}