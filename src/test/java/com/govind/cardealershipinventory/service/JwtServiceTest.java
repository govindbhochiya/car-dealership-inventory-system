package com.govind.cardealershipinventory.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    @Test
    void shouldValidateTokenSuccessfully() {

        // Arrange
        String token = jwtService.generateToken("govind@gmail.com");

        // Act
        boolean isValid = jwtService.isTokenValid(token, "govind@gmail.com");

        // Assert
        assertTrue(isValid);
    }
}