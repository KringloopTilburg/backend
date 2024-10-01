package nl.kringlooptilburg.apigateway.security;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
class JwtServerAuthenticationConverter implements ServerAuthenticationConverter {

    private final JwtService jwtService;
    private static final String BEARER = "Bearer ";

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
                .filter(header -> header.startsWith(BEARER))
                .map(header -> header.substring(BEARER.length()))
                .map(token -> new JwtToken(token, createUserDetails(token)));
    }

    private UserDetails createUserDetails(String token) {
        try {
            Claims claims = jwtService.getAllClaimsFromToken(token);
            return User.builder()
                    .username(claims.getSubject())
                    .authorities(claims.getOrDefault("role", "").toString())
                    .password("")
                    .build();
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("Invalid token: " + e.getMessage());
        }
    }
}
