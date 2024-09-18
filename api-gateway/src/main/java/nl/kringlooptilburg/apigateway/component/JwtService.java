package nl.kringlooptilburg.apigateway.component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtService {

    private String SECRET_KEY = "G7RtBxU4fVQx9z7vT1iX0WzQEQBmcCKb";

    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public Claims getAllClaimsFromToken(String token) throws IllegalAccessException {
        try {
            return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        } catch (JwtException | IllegalArgumentException exception) {
            throw new IllegalAccessException("Invalid token: " + exception.getMessage());
        }
    }

    private boolean isTokenExpired(String token) {
        try {
            return this.getAllClaimsFromToken(token).getExpiration().before(new Date());
        } catch (IllegalAccessException e) {
            return true;
        }
    }

    public boolean isInvalid(String token) {
        return this.isTokenExpired(token);
    }
}
