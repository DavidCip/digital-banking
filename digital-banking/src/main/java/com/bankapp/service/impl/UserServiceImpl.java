package com.bankapp.service.impl;

import com.bankapp.dto.request.UpdatePasswordRequest;
import com.bankapp.dto.request.UpdatePhoneRequest;
import com.bankapp.dto.response.UserResponse;
import com.bankapp.entity.User;
import com.bankapp.repository.UserRepository;
import com.bankapp.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponse getUserById(Long userId) {
        User user = getUser(userId);
        return mapToResponse(user);
    }

    @Override
    @Transactional
    public UserResponse updatePhone(Long userId, UpdatePhoneRequest request) {
        User user = getUser(userId);

        if (userRepository.existsByPhone(request.getPhone())) {
            throw new RuntimeException("Phone already exists");
        }

        user.setPhone(request.getPhone());

        User savedUser = userRepository.save(user);

        return mapToResponse(savedUser);
    }

    @Override
    @Transactional
    public UserResponse updatePassword(Long userId, UpdatePasswordRequest request) {
        User user = getUser(userId);

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        User savedUser = userRepository.save(user);

        return mapToResponse(savedUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        User user = getUser(userId);

        user.setActive(false);

        userRepository.save(user);
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private UserResponse mapToResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getCnp(),
                user.getPhone(),
                user.isActive(),
                user.getCreatedAt()
        );
    }
}