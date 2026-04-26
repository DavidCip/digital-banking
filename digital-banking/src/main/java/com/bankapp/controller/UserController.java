package com.bankapp.controller;

import com.bankapp.config.ApiPaths;
import com.bankapp.dto.request.UpdatePasswordRequest;
import com.bankapp.dto.request.UpdatePhoneRequest;
import com.bankapp.dto.response.UserResponse;
import com.bankapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiPaths.USERS)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(ApiPaths.BY_ID)
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PutMapping(ApiPaths.UPDATE_PHONE)
    public ResponseEntity<UserResponse> updatePhone(
            @PathVariable Long userId,
            @Valid @RequestBody UpdatePhoneRequest request
    ) {
        return ResponseEntity.ok(userService.updatePhone(userId, request));
    }

    @PutMapping(ApiPaths.UPDATE_PASSWORD)
    public ResponseEntity<UserResponse> updatePassword(
            @PathVariable Long userId,
            @Valid @RequestBody UpdatePasswordRequest request
    ) {
        return ResponseEntity.ok(userService.updatePassword(userId, request));
    }

    @DeleteMapping(ApiPaths.BY_ID)
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}