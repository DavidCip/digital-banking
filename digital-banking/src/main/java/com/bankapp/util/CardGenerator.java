package com.bankapp.util;

import java.security.SecureRandom;

public class CardGenerator {

    private static final SecureRandom random = new SecureRandom();

    public static String generateCardNumber() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 16; i++) {
            sb.append(random.nextInt(10));
        }

        return sb.toString();
    }
}