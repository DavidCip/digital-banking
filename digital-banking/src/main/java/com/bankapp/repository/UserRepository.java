package com.bankapp.repository;

import com.bankapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    boolean existsByCnp(String cnp);
    boolean existsByPhone(String phone);
    Optional<User> findByUsername(String username);
}
