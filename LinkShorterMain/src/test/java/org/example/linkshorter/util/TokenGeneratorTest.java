package org.example.linkshorter.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenGeneratorTest {

    @Test
    void testGenerateTokenForValidString() {
        TokenGenerator tokenGenerator = new TokenGenerator(8);
        String token = tokenGenerator.generateTokenForString("abcd");
        assertNotNull(token);
    }

    @Test
    void testGenerateTokenForNull() {
        TokenGenerator tokenGenerator = new TokenGenerator(8);
        assertThrows(IllegalArgumentException.class, () -> tokenGenerator.generateTokenForString(null));
    }

    @Test
    void testGenerateTokenForEmptyString() {
        TokenGenerator tokenGenerator = new TokenGenerator(8);
        assertThrows(IllegalArgumentException.class, () -> tokenGenerator.generateTokenForString(""));
    }

    @Test
    void testGenerateLongTokenForString() {
        TokenGenerator tokenGenerator = new TokenGenerator(128);
        String str = "abcdefghijklmnopqrstuvwxyz";
        String token = tokenGenerator.generateTokenForString(str);
        assertNotNull(token);
        assertEquals(128, token.length());
    }

    @Test
    void testCreateTokenGeneratorForTooShortToken() {
        assertThrows(IllegalArgumentException.class, () -> new TokenGenerator(4));
    }

    @Test
    void testGenerateTokenForEqualStrings() {
        TokenGenerator tokenGenerator = new TokenGenerator(8);
        String str1 = "Equal string";
        String str2 = "Equal string";
        String token1 = tokenGenerator.generateTokenForString(str1);
        String token2 = tokenGenerator.generateTokenForString(str2);
        assertEquals(token1, token2);
    }

}