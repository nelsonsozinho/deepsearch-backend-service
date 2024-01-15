package com.axreng.backend.utils;

import java.security.SecureRandom;

public class IdUtils {

    private static final String CHARACTERS = "abcdefghijlmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static final int LENGTH_CODE = 8;

    public static String generateId() {
        final var random = new SecureRandom();
        final var sb = new StringBuilder(LENGTH_CODE);

        for (int i = 0; i < LENGTH_CODE; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }

}
