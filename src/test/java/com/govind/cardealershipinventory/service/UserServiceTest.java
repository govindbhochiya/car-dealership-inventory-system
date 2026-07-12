package com.govind.cardealershipinventory.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.govind.cardealershipinventory.entity.User;
import com.govind.cardealershipinventory.repository.UserRepository;

class UserServiceTest {
	UserRepository userRepository = Mockito.mock(UserRepository.class);
	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	private final UserService userService =
	        new UserService(userRepository, passwordEncoder);
    

    @Test
    void shouldRegisterUserSuccessfully() {

        // Arrange
        User user = new User();
        user.setFullName("Govind");
        user.setEmail("govind@gmail.com");
        user.setPassword("Password@123");
        user.setRole("USER");
        when(userRepository.save(user)).thenReturn(user);
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
        user.setPassword("Password@123");
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
        user.setPassword("Password@123");
        user.setRole("USER");

        // Act
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> userService.register(user)
        );

        // Assert
        assertEquals("Full name is required", exception.getMessage());
    }
    @Test
    void shouldThrowExceptionWhenEmailFormatIsInvalid() {

        // Arrange
        User user = new User();
        user.setFullName("Govind");
        user.setEmail("govindgmail.com");
        user.setPassword("Password@123");
        user.setRole("USER");

        // Act
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> userService.register(user)
        );

        // Assert
        assertEquals("Invalid email format", exception.getMessage());
    }
    @Test
    void shouldThrowExceptionWhenPasswordFormatIsInvalid() {

        // Arrange
        User user = new User();
        user.setFullName("Govind");
        user.setEmail("govind@gmail.com");
        user.setPassword("Password");
        user.setRole("USER");

        // Act
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> userService.register(user)
        );

        // Assert
        assertEquals("Invalid password format", exception.getMessage());
    }
    @Test
    void shouldSaveUserWhenRegistrationIsValid() {

        // Arrange
        User user = new User();
        user.setFullName("Govind");
        user.setEmail("govind@gmail.com");
        user.setPassword("Password@123");
        user.setRole("USER");

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);

        // Act
        User savedUser = userService.register(user);

        // Assert
        assertNotNull(savedUser);
        assertEquals("Govind", savedUser.getFullName());
        assertEquals("govind@gmail.com", savedUser.getEmail());

        verify(userRepository).save(user);
    }
    @Test
    void shouldThrowExceptionWhenEmailFormatIsInvalidAtLogin() {

        // Act
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> userService.login("govindgmail.com", "Password@123")
        );

        // Assert
        assertEquals("Invalid email format", exception.getMessage());
    }
    @Test
    void shouldThrowExceptionWhenPasswordFormatIsInvalidAtLogin() {

        // Act
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> userService.login("govind@gmail.com", "password")
        );

        // Assert
        assertEquals("Invalid password format", exception.getMessage());
    }
    @Test
    void shouldLoginSuccessfullyWithValidCredentials() {

        // Arrange
        User user = new User();
        user.setEmail("govind@gmail.com");
        user.setPassword("Password@123");

        when(userRepository.findByEmail("govind@gmail.com"))
                .thenReturn(Optional.of(user));

        // Act
        User loggedInUser = userService.login("govind@gmail.com", "Password@123");

        // Assert
        assertNotNull(loggedInUser);
        assertEquals("govind@gmail.com", loggedInUser.getEmail());
    }
    
    @Test
    void shouldThrowExceptionWhenEmailDoesNotExist() {

        // Arrange
        when(userRepository.findByEmail("govind@gmail.com"))
                .thenReturn(Optional.empty());

        // Act
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> userService.login("govind@gmail.com", "Password@123")
        );

        // Assert
        assertEquals("Email not found", exception.getMessage());
    }
    @Test
    void shouldHashPasswordBeforeSavingUser() {

        // Arrange
        User user = new User();
        user.setFullName("Govind");
        user.setEmail("govind@gmail.com");
        user.setPassword("Password@123");
        user.setRole("USER");

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(userRepository.save(user)).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        User savedUser = userService.register(user);

        // Assert
        assertNotEquals("Password@123", savedUser.getPassword());
    }
}