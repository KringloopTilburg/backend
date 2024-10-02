package nl.kringlooptilburg.authenticationservice.service;

import io.jsonwebtoken.lang.Assert;
import lombok.RequiredArgsConstructor;
import nl.kringlooptilburg.authenticationservice.exception.InvalidCredentialsException;
import nl.kringlooptilburg.authenticationservice.model.AuthenticationRequest;
import nl.kringlooptilburg.authenticationservice.model.AuthenticationResponse;
import nl.kringlooptilburg.authenticationservice.model.User;
import nl.kringlooptilburg.authenticationservice.publisher.LogPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;
    private final LogPublisher logPublisher;
    private final RoleService roleService;

    private final BCryptPasswordEncoder passwordEncoder;

    public AuthenticationResponse register(AuthenticationRequest authRequest) {
        authRequest.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        logPublisher.publishLog("User register request for email: " + authRequest.getEmail());

        User newUser = new User();
        newUser.setId(UUID.randomUUID().toString());
        newUser.setEmail(authRequest.getEmail());
        newUser.setPassword(authRequest.getPassword());
        newUser.setRole(roleService.findByName("ROLE_USER"));

        User user = customUserDetailsService.save(newUser);
        Assert.notNull(user, "Failed to register user. Please try again later");

        String accessToken = jwtService.generate(user, "ACCESS");
        String refreshToken = jwtService.generate(user, "REFRESH");

        return new AuthenticationResponse(accessToken, refreshToken);
    }

    public AuthenticationResponse login(AuthenticationRequest authRequest) {
        User user = customUserDetailsService.findByEmail(authRequest.getEmail());
        if (passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            String accessToken = jwtService.generate(user, "ACCESS");
            String refreshToken = jwtService.generate(user, "REFRESH");

            return new AuthenticationResponse(accessToken, refreshToken);
        }
        throw new InvalidCredentialsException("Ongeldige e-mail/wachtwoord combinatie.");
    }
}
