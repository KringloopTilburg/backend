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
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@ActiveProfiles("test")
class AuthenticationServiceTests {

    @Mock
    private JwtService jwtService;
    @Mock
    private CustomUserDetailsService customUserDetailsService;
    @Mock
    private LogPublisher logPublisher;
    @Mock
    private RoleService roleService;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    @DisplayName("Registers new user")
    void testRegisterSuccess() {
        // Arrange
        AuthenticationRequest authRequest = new AuthenticationRequest();
        authRequest.setEmail("test@example.com");
        authRequest.setPassword("password");

        User newUser = new User();
        newUser.setEmail("test@example.com");
        newUser.setPassword("encodedPassword");
        newUser.setRole(new Role(1L, "ROLE_USER"));

        when(passwordEncoder.encode(authRequest.getPassword())).thenReturn("encodedPassword");
        when(roleService.findByName("ROLE_USER")).thenReturn(new Role(1L, "ROLE_USER"));
        when(customUserDetailsService.save(any())).thenReturn(newUser);
        when(jwtService.generate(any(User.class), eq("ACCESS"))).thenReturn("accessToken");
        when(jwtService.generate(any(User.class), eq("REFRESH"))).thenReturn("refreshToken");

        // Act
        AuthenticationResponse response = authenticationService.register(authRequest);

        // Assert
        assertNotNull(response);
        assertEquals("accessToken", response.getAccessToken());
        assertEquals("refreshToken", response.getRefreshToken());
    }

    @Test
    void testRegister_failure_userNotSaved() {
        // Arrange
        AuthenticationRequest authRequest = new AuthenticationRequest("test@example.com", "password123");
        Role role = new Role(1L, "ROLE_USER");

        when(passwordEncoder.encode(authRequest.getPassword())).thenReturn("encodedPassword");
        when(roleService.findByName("ROLE_USER")).thenReturn(role);
        when(customUserDetailsService.save(any(User.class))).thenReturn(null);

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.register(authRequest);
        });

        // Assert
        assertEquals("Failed to register user. Please try again later", exception.getMessage());
    }


    @Test
    void testLogin_successful() {
        // Arrange
        AuthenticationRequest authRequest = new AuthenticationRequest("test@example.com", "password123");
        User user = new User();
        user.setUserId(1);
        user.setEmail(authRequest.getEmail());
        user.setPassword("encodedPassword");
        user.setRole(new Role(1L, "ROLE_USER"));

        when(customUserDetailsService.findByEmail(authRequest.getEmail())).thenReturn(user);
        when(passwordEncoder.matches(authRequest.getPassword(), user.getPassword())).thenReturn(true);
        when(jwtService.generate(user, "ACCESS")).thenReturn("accessToken");
        when(jwtService.generate(user, "REFRESH")).thenReturn("refreshToken");

        // Act
        AuthenticationResponse response = authenticationService.login(authRequest);

        // Assert
        assertNotNull(response);
        assertEquals("accessToken", response.getAccessToken());
        assertEquals("refreshToken", response.getRefreshToken());
    }

    @Test
    void testLogin_invalidCredentials() {
        // Arrange
        AuthenticationRequest authRequest = new AuthenticationRequest("test@example.com", "wrongPassword");
        User user = new User();
        user.setEmail(authRequest.getEmail());
        user.setPassword("encodedPassword");

        when(customUserDetailsService.findByEmail(authRequest.getEmail())).thenReturn(user);
        when(passwordEncoder.matches(authRequest.getPassword(), user.getPassword())).thenReturn(false); // Invalid password

        // Act
        InvalidCredentialsException exception = assertThrows(InvalidCredentialsException.class, () -> {
            authenticationService.login(authRequest);
        });

        // Assert
        assertEquals("Ongeldige e-mail/wachtwoord combinatie.", exception.getMessage());
    }
}

