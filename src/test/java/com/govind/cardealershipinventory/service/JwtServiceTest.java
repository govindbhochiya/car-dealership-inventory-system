package com.govind.cardealershipinventory.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class JwtServiceTest {

    @Test
    void shouldGenerateTokenForValidUser() {

        // Arrange
        JwtService jwtService = new JwtService();

        // Act
        String token = jwtService.generateToken("govind@gmail.com");

        // Assert
        assertNotNull(token);
        assertFalse(token.isBlank());
    }
}