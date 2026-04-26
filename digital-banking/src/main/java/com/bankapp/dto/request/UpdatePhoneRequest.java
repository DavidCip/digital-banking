package com.bankapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UpdatePhoneRequest {

    @NotBlank
    @Pattern(regexp = "\\d{10}", message = "Phone must have exactly 10 digits")
    private String phone;

    public String getPhone() {
        return phone;
    }
}