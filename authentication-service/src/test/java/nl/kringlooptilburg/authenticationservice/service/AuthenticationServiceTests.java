package nl.kringlooptilburg.authenticationservice.service;

import nl.kringlooptilburg.authenticationservice.exception.InvalidCredentialsException;
import nl.kringlooptilburg.authenticationservice.model.AuthenticationRequest;
import nl.kringlooptilburg.authenticationservice.model.AuthenticationResponse;
import nl.kringlooptilburg.authenticationservice.model.User;
import nl.kringlooptilburg.authenticationservice.model.Role;
import nl.kringlooptilburg.authenticationservice.publisher.LogPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class AuthenticationServiceTests {

    @Mock
    private JwtService jwtService;

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private LogPublisher logPublisher;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    public void setup() {
        Mockito.doNothing().when(logPublisher).publishLog(any(String.class));
    }

    @Test
    @DisplayName("Should be able to register a new user")
    void TEST_register_001() {
        // Arrange
        AuthenticationRequest authRequest = new AuthenticationRequest("test@example.com", "password");

        User newUser = new User();
        newUser.setEmail(authRequest.getEmail());
        newUser.setPassword(authRequest.getPassword());
        newUser.setRole(Role.USER);

        when(customUserDetailsService.save(any())).thenReturn(newUser);
        when(jwtService.generate(any(), anyString())).thenReturn("access_token");

        // Act
        AuthenticationResponse response = authenticationService.register(authRequest);

        // Assert
        assertNotNull(response);
        assertEquals("access_token", response.getAccessToken());
        assertNotNull(response.getRefreshToken());
    }

    @Test
    @DisplayName("Should return null if user is not saved successfully")
    void TEST_register_002() {
        // Arrange
        AuthenticationRequest authRequest = new AuthenticationRequest("test@example.com", "password");

        // Mock userRepositoryService.save() om null terug te geven
        when(customUserDetailsService.save(any())).thenReturn(null);

        // Assert
        assertThrows(IllegalArgumentException.class, () -> authenticationService.register(authRequest));
    }

    @Test
    @DisplayName("Should be able to login with valid credentials")
    void TEST_login_001() {
        // Arrange
        AuthenticationRequest authRequest = new AuthenticationRequest("test@example.com", "password");

        User existingUser = new User();
        existingUser.setEmail(authRequest.getEmail());
        existingUser.setPassword(authRequest.getPassword());
        existingUser.setRole(Role.USER);

        when(customUserDetailsService.findByEmail(authRequest.getEmail())).thenReturn(existingUser);
        when(passwordEncoder.matches(authRequest.getPassword(), existingUser.getPassword())).thenReturn(true);
        when(jwtService.generate(any(), anyString())).thenReturn("access_token");

        // Act
        AuthenticationResponse response = authenticationService.login(authRequest);

        // Assert
        assertNotNull(response);
        assertEquals("access_token", response.getAccessToken());
        assertNotNull(response.getRefreshToken());
    }

    @Test
    @DisplayName("Should throw exception when login with invalid credentials")
    void TEST_login_002() {
        // Arrange
        AuthenticationRequest authRequest = new AuthenticationRequest("test@example.com", "password");

        when(customUserDetailsService.findByEmail(anyString())).thenThrow(InvalidCredentialsException.class);

        // Assert
        assertThrows(InvalidCredentialsException.class, () -> authenticationService.login(authRequest));
    }
}

