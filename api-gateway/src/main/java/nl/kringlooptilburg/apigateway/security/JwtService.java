package nl.kringlooptilburg.apigateway.security;

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

    private final String SECRET_KEY = "G7RtBxU4fVQx9z7vT1iX0WzQEQBmcCKb";

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

    public boolean isValid(String token) {
        try {
            Claims claims = this.getAllClaimsFromToken(token);
            return claims.getExpiration().after(new Date());
        } catch (IllegalAccessException e) {
            return true;
        }
    }
}
