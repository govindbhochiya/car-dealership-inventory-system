package com.govind.cardealershipinventory.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.govind.cardealershipinventory.entity.User;
import com.govind.cardealershipinventory.repository.UserRepository;

class UserServiceTest {
	UserRepository userRepository = Mockito.mock(UserRepository.class);
   
    private final UserService userService = new UserService(userRepository);

    @Test
    void shouldRegisterUserSuccessfully() {

        // Arrange
        User user = new User();
        user.setFullName("Govind");
        user.setEmail("govind@gmail.com");
        user.setPassword("password123");
        user.setRole("USER");

        // Act
        User registeredUser = userService.register(user);

        // Assert
        assertNotNull(registeredUser);
    }
    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {

        // Arrange
      

        User user = new User();
        user.setFullName("Govind");
        user.setEmail("govind@gmail.com");
        user.setPassword("password123");
        user.setRole("USER");

        when(userRepository.existsByEmail("govind@gmail.com"))
                .thenReturn(true);

        // Act & Assert
        assertThrows(RuntimeException.class,
                () -> userService.register(user));
    }
    @Test
    void shouldThrowExceptionWhenFullNameIsEmpty() {

        // Arrange
        User user = new User();
        user.setFullName("");
        user.setEmail("govind@gmail.com");
        user.setPassword("password123");
        user.setRole("USER");

        // Act
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> userService.register(user)
        );

        // Assert
        assertEquals("Full name is required", exception.getMessage());
    }
}