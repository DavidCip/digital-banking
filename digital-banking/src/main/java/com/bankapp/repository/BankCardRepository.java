package com.bankapp.repository;

import com.bankapp.entity.BankCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankCardRepository extends JpaRepository<BankCard, Long> {

    boolean existsByCardNumber(String cardNumber);

    List<BankCard> findByUserId(Long userId);
}