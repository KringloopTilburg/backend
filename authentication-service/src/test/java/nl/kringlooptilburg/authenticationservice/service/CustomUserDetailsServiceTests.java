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
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class CustomUserDetailsServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private LogPublisher logPublisher;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    public void setup() {
        Mockito.doNothing().when(logPublisher).publishLog(any(String.class));
    }

    @Test
    @DisplayName("Should be able to save a new user.")
    void TEST_save_001() {
        // Arrange
        var user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setRole(Role.USER);

        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);

        // Act
        var response = customUserDetailsService.save(user);

        // Assert
        assertNotNull(response);
        assertEquals(user.getUserId(), response.getUserId());
        assertEquals(user.getEmail(), response.getEmail());
        assertEquals(user.getPassword(), response.getPassword());
        assertEquals(user.getRole(), response.getRole());
    }

    @Test
    @DisplayName("Should not be able to save an existing user")
    void TEST_save_002() {
        // Arrange
        var user = new User(1, "test@example.com", "password", Role.USER);

        // Act
        when(userRepository.existsByEmail(any())).thenReturn(true);

        // Assert
        assertThrows(EmailAlreadyExistsException.class, () -> customUserDetailsService.save(user));
    }

    @Test
    @DisplayName("Should find user with correct email and password")
    void TEST_findByEmailAndPassword_001() {
        // Arrange
        var email = "test@example.com";
        var password = "password";
        var user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(Role.USER);

        when(userRepository.findByEmailAndPassword(anyString(), anyString())).thenReturn(user);

        // Act
        var response = customUserDetailsService.findByEmailAndPassword(email, password);

        // Assert
        assertNotNull(response);
        assertEquals(user.getEmail(), response.getEmail());
        assertEquals(user.getPassword(), response.getPassword());
        assertEquals(user.getRole(), response.getRole());
    }

    @Test
    @DisplayName("Should not find user with incorrect email and password")
    void TEST_findByEmailAndPassword_002() {
        // Arrange
        var email = "test@example.com";
        var password = "password";

        when(userRepository.findByEmailAndPassword(anyString(), anyString())).thenReturn(null);

        // Assert
        assertThrows(InvalidCredentialsException.class, () -> customUserDetailsService.findByEmailAndPassword(email, password));
    }

    @Test
    @DisplayName("Should find user by ID")
    void TEST_findById_001() throws IllegalAccessException {
        // Arrange
        var userId = 1;
        var user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setRole(new Role(1L, "ROLE_USER"));

        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));

        // Act
        var response = customUserDetailsService.findById(userId);

        //Assert
        assertNotNull(response);
        assertEquals(user.getEmail(), response.getEmail());
        assertEquals(user.getPassword(), response.getPassword());
        assertEquals(user.getRole(), response.getRole());
    }

    @Test
    @DisplayName("Should not find user by ID")
    void TEST_findById_002() {
        // Arrange
        var userId = 1;

        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Assert
        assertThrows(IllegalAccessException.class, () -> customUserDetailsService.findById(userId));
    }

    @Test
    @DisplayName("Should find user by email")
    void TEST_findByEmail_001() {
        // Arrange
        var email = "test@example.com";
        var user = new User();
        user.setEmail(email);
        user.setPassword("password");
        user.setRole(Role.USER);

        when(userRepository.findByEmail(anyString())).thenReturn(user);

        // Act
        var response = customUserDetailsService.findByEmail(email);

        // Assert
        assertNotNull(response);
        assertEquals(user.getEmail(), response.getEmail());
        assertEquals(user.getPassword(), response.getPassword());
        assertEquals(user.getRole(), response.getRole());
    }

    @Test
    @DisplayName("Should not find user by email")
    void TEST_findByEmail_002() {
        // Arrange
        var email = "test@example.com";

        when(userRepository.findByEmail(anyString())).thenReturn(null);

        // Assert
        assertThrows(InvalidCredentialsException.class, () -> customUserDetailsService.findByEmail(email));
    }
}
