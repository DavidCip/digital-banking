package com.bankapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterResponse {

    private String message;
    private Long userId;
    private String username;
    private String cardNumber;
}