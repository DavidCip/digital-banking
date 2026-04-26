package com.bankapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Size(min = 4, max = 50)
    private String username;

    @NotBlank
    @Size(min = 6)
    private String password;

    @NotBlank
    @Pattern(regexp = "\\d{13}", message = "CNP must have exactly 13 digits")
    private String cnp;

    @NotBlank
    @Pattern(regexp = "\\d{10}", message = "Phone must have exactly 10 digits")
    private String phone;
}