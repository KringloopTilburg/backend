package nl.kringlooptilburg.authenticationservice.service;

import io.jsonwebtoken.lang.Assert;
import lombok.RequiredArgsConstructor;
import nl.kringlooptilburg.authenticationservice.exception.InvalidCredentialsException;
import nl.kringlooptilburg.authenticationservice.model.AuthenticationRequest;
import nl.kringlooptilburg.authenticationservice.model.AuthenticationResponse;
import nl.kringlooptilburg.authenticationservice.model.User;
import nl.kringlooptilburg.authenticationservice.model.UserRole;
import nl.kringlooptilburg.authenticationservice.publisher.LogPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtService jwtService;
    private final UserRepositoryService userRepositoryService;
    private final LogPublisher logPublisher;

    private final BCryptPasswordEncoder passwordEncoder;

    public AuthenticationResponse register(AuthenticationRequest authRequest) {
        authRequest.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        logPublisher.publishLog("User register request for email: " + authRequest.getEmail());

        var newUser = new User();
        newUser.setEmail(authRequest.getEmail());
        newUser.setPassword(authRequest.getPassword());
        newUser.setRole(UserRole.USER);

        var user = userRepositoryService.save(newUser);
        Assert.notNull(user, "Failed to register user. Please try again later");

        String accessToken = jwtService.generate(user, "ACCESS");
        String refreshToken = jwtService.generate(user, "REFRESH");

        return new AuthenticationResponse(accessToken, refreshToken);
    }

    public AuthenticationResponse login(AuthenticationRequest authRequest) {
        var user = userRepositoryService.findByEmail(authRequest.getEmail());
        if (passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            String accessToken = jwtService.generate(user, "ACCESS");
            String refreshToken = jwtService.generate(user, "REFRESH");

            return new AuthenticationResponse(accessToken, refreshToken);
        }
        throw new InvalidCredentialsException("Ongeldige e-mail/wachtwoord combinatie.");
    }
}