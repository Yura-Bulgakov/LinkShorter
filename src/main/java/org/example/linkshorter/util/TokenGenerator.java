package org.example.linkshorter.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Component
public class TokenGenerator {
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefjhijklmnopqrstuvwxyz";
    private static final int BASE = ALPHABET.length();
    private final int tokenLength;
    private final MessageDigest md;

    public TokenGenerator(@Value("${token.length:8}") int tokenLength) {
        if (tokenLength < 5) {
            throw new IllegalArgumentException("Длинна токена должна быть больше 5");
        }
        this.tokenLength = tokenLength;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Не удалось получить экземпляр MessageDigest", e);
        }
    }

    public String generateTokenForString(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Строка не должна быть пустой");
        }
        List<Integer> hashBytes = getListHashBytesByLength(str.getBytes(), tokenLength);
        StringBuilder token = new StringBuilder();
        for (Integer hashByte : hashBytes) {
            token.append(ALPHABET.charAt(hashByte % BASE));
        }
        return token.toString();
    }

    private List<Integer> getListHashBytesByLength(byte[] bytes, int length) {
        List<Integer> unsignedBytes = new ArrayList<>();
        byte[] hashBytes = md.digest(bytes);
        for (int i = 0; i < Math.min(length, hashBytes.length); i++) {
            int unsignedByte = hashBytes[i] & 0xFF;
            unsignedBytes.add(unsignedByte);
        }
        for (int i = 0; unsignedBytes.size() < length; i++) {
            unsignedBytes.add(unsignedBytes.get(i));
        }
        return unsignedBytes;
    }


}
