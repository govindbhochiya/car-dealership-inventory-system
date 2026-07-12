package com.govind.cardealershipinventory.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class JwtServiceTest {
	JwtService jwtService = new JwtService();
    @Test
    void shouldGenerateTokenForValidUser() {

        // Arrange
        
        // Act
        String token = jwtService.generateToken("govind@gmail.com");

        // Assert
        assertNotNull(token);
        assertFalse(token.isBlank());
    }
    @Test
    void shouldExtractUsernameFromToken() {

        // Arrange
        String token = jwtService.generateToken("govind@gmail.com");

        // Act
        String username = jwtService.extractUsername(token);

        // Assert
        assertEquals("govind@gmail.com", username);
    }
}