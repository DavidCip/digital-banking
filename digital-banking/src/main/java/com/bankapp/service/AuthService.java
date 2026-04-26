package com.bankapp.service;

import com.bankapp.dto.request.LoginRequest;
import com.bankapp.dto.request.RegisterRequest;
import com.bankapp.dto.response.LoginResponse;
import com.bankapp.dto.response.RegisterResponse;

public interface AuthService {

    RegisterResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);
}