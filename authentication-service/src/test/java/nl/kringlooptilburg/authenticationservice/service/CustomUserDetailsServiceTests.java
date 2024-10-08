package nl.kringlooptilburg.authenticationservice.service;

import nl.kringlooptilburg.authenticationservice.exception.EmailAlreadyExistsException;
import nl.kringlooptilburg.authenticationservice.exception.InvalidCredentialsException;
import nl.kringlooptilburg.authenticationservice.model.User;
import nl.kringlooptilburg.authenticationservice.model.Role;
import nl.kringlooptilburg.authenticationservice.publisher.LogPublisher;
import nl.kringlooptilburg.authenticationservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private LogPublisher logPublisher;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;



    @Test
    void testSave_successful() {
        // Arrange
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password123");

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);

        // Act
        User savedUser = customUserDetailsService.save(user);

        // Assert
        assertNotNull(savedUser);
    }

    @Test
    void testSave_emailAlreadyExists() {
        // Arrange
        User user = new User();
        user.setEmail("test@example.com");

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        // Act
        EmailAlreadyExistsException exception = assertThrows(EmailAlreadyExistsException.class, () -> {
            customUserDetailsService.save(user);
        });
        // Assert
        assertEquals("Email address already exists.", exception.getMessage());
    }

    @Test
    void testFindByEmailAndPassword_successful() {
        // Arrange
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password123");

        when(userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword())).thenReturn(user);

        // Act
        User foundUser = customUserDetailsService.findByEmailAndPassword(user.getEmail(), user.getPassword());

        // Assert
        assertNotNull(foundUser);
        assertEquals("test@example.com", foundUser.getEmail());
    }

    @Test
    void testFindByEmailAndPassword_invalidCredentials() {
        // Arrange
        when(userRepository.findByEmailAndPassword(anyString(), anyString())).thenReturn(null);

        // Act
        InvalidCredentialsException exception = assertThrows(InvalidCredentialsException.class, () -> {
            customUserDetailsService.findByEmailAndPassword("invalid@example.com", "wrongPassword");
        });
        // Assert
        assertEquals("Invalid email or password", exception.getMessage());
    }

    @Test
    void testFindById_successful() throws IllegalAccessException {
        // Arrange
        User user = new User();
        user.setUserId(1);
        user.setEmail("test@example.com");

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        // Act
        User foundUser = customUserDetailsService.findById(1);

        // Assert
        assertNotNull(foundUser);
        assertEquals(1, foundUser.getUserId());
        assertEquals("test@example.com", foundUser.getEmail());
    }

    @Test
    void testFindById_userNotFound() {
        // Arrange
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Act
        IllegalAccessException exception = assertThrows(IllegalAccessException.class, () -> {
            customUserDetailsService.findById(1);
        });
        // Assert
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testFindByEmail_successful() {
        // Arrange
        User user = new User();
        user.setEmail("test@example.com");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        // Act
        User foundUser = customUserDetailsService.findByEmail(user.getEmail());

        // Assert
        assertNotNull(foundUser);
        assertEquals("test@example.com", foundUser.getEmail());
    }

    @Test
    void testFindByEmail_invalidCredentials() {
        // Arrange
        when(userRepository.findByEmail(anyString())).thenReturn(null);

        // Act
        InvalidCredentialsException exception = assertThrows(InvalidCredentialsException.class, () -> {
            customUserDetailsService.findByEmail("invalid@example.com");
        });
        // Assert
        assertEquals("Invalid email or password", exception.getMessage());
    }

    @Test
    void testLoadUserByUsername_successful() {
        // Arrange
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setUserId(1);
        user.setRole(new Role(1L, "ROLE_USER"));

        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        // Act
        UserDetails userDetails = customUserDetailsService.loadUserByUsername("test@example.com");

        // Assert
        assertNotNull(userDetails);
        assertEquals("test@example.com", userDetails.getUsername());
        assertEquals("password123", userDetails.getPassword());
    }

    @Test
    void testLoadUserByUsername_userNotFound() {
        // Arrange
        when(userRepository.findByEmail(anyString())).thenReturn(null);

        // Act
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername("notfound@example.com");
        });
        // Assert
        assertEquals("User not found", exception.getMessage());
    }
}
