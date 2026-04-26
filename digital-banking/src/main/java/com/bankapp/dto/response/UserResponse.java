package com.bankapp.dto.response;

import java.time.LocalDateTime;

public class UserResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String cnp;
    private String phone;
    private boolean active;
    private LocalDateTime createdAt;

    public UserResponse(Long id, String firstName, String lastName, String username,
                        String cnp, String phone, boolean active, LocalDateTime createdAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.cnp = cnp;
        this.phone = phone;
        this.active = active;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getUsername() { return username; }
    public String getCnp() { return cnp; }
    public String getPhone() { return phone; }
    public boolean isActive() { return active; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}