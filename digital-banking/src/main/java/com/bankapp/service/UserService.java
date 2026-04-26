package com.bankapp.service;

import com.bankapp.dto.request.UpdatePasswordRequest;
import com.bankapp.dto.request.UpdatePhoneRequest;
import com.bankapp.dto.response.UserResponse;

public interface UserService {

    UserResponse getUserById(Long userId);

    UserResponse updatePhone(Long userId, UpdatePhoneRequest request);

    UserResponse updatePassword(Long userId, UpdatePasswordRequest request);

    void deleteUser(Long userId);
}