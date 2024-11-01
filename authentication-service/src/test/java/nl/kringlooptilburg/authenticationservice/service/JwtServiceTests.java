package nl.kringlooptilburg.authenticationservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import nl.kringlooptilburg.authenticationservice.model.User;
import nl.kringlooptilburg.authenticationservice.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class JwtServiceTests {
    @InjectMocks
    private JwtService jwtService;
    private Key key;
    private final String SECRET_KEY = "G7RtBxU4fVQx9z7vT1iX0WzQEQBmcCKb";


    @BeforeEach
    void setUp() {
        jwtService.init();
        this.key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    @Test
    @DisplayName("Should generate valid access token")
    void testGenerateAccessToken() {
        // Arrange
        User user = new User(1, "test@example.com", "password", new Role());

        // Act
        String accessToken = jwtService.generate(user, "ACCESS");

        // Assert
        assertDoesNotThrow(() -> Jwts.parser().setSigningKey(key).parseClaimsJws(accessToken));
    }

    @Test
    @DisplayName("Should generate valid refresh token")
    void testGenerateRefreshToken() {
        // Arrange
        User user = new User(1, "test@example.com", "password", new Role());

        // Act
        String refreshToken = jwtService.generate(user, "REFRESH");

        // Assert
        assertDoesNotThrow(() -> Jwts.parser().setSigningKey(key).parseClaimsJws(refreshToken));
    }

    @Test
    @DisplayName("Should extract claims from token")
    void testGetAllClaimsFromToken() {
        // Arrange
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", "user123");
        claims.put("role", "USER");
        Date expirationDate = new Date(System.currentTimeMillis() + 3600000); // 1 hour from now

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject("test@example.com")
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();

        // Act
        Claims extractedClaims = jwtService.getAllClaimsFromToken(token);

        // Assert
        assertEquals("user123", extractedClaims.get("id"));
        assertEquals("USER", extractedClaims.get("role"));
    }

    @Test
    @DisplayName("Should return expiration date from token")
    void testGetExpirationDateFromToken() {
        // Arrange
        Date expirationDate = new Date(System.currentTimeMillis() + 3600000); // 1 hour from now
        String token = Jwts.builder()
                .setSubject("test@example.com")
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();

        // Act
        Date extractedExpirationDate = jwtService.getExpirationDateFromToken(token);

        // Assert
        long difference = Math.abs(expirationDate.getTime() - extractedExpirationDate.getTime());
        assertTrue(difference <= 1000, "Expiration dates are not within 1 second of each other");
    }

    @Test
    @DisplayName("Should return true if token is valid")
    void testValidateToken() {
        // Arrange
        Date expirationDate = new Date(System.currentTimeMillis() + 3600000); // 1 hour from now
        String token = Jwts.builder()
                .setSubject("test@example.com")
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();

        // Act
        boolean isValid = jwtService.validateToken(token);

        // Assert
        assertTrue(isValid);
    }

    @Test
    @DisplayName("Should return false if token is expired")
    void testValidateExpiredToken() {
        // Arrange
        Date expirationDate = new Date(System.currentTimeMillis() - 3600000); // 1 hour ago
        String token = Jwts.builder()
                .setSubject("test@example.com")
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();

        // Act
        boolean isValid = jwtService.validateToken(token);

        // Assert
        assertFalse(isValid);
    }
}
