package nl.kringlooptilburg.authenticationservice.controller;

import lombok.RequiredArgsConstructor;
import nl.kringlooptilburg.authenticationservice.exception.EmailAlreadyExistsException;
import nl.kringlooptilburg.authenticationservice.exception.InvalidCredentialsException;
import nl.kringlooptilburg.authenticationservice.model.AuthenticationRequest;
import nl.kringlooptilburg.authenticationservice.model.AuthenticationResponse;
import nl.kringlooptilburg.authenticationservice.service.AuthenticationService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;
    private static final Log LOGGER = LogFactory.getLog(AuthController.class); // google cloud logging


    @PostMapping("/register")
    public AuthenticationResponse register(@RequestBody AuthenticationRequest authRequest) {
        try {
            return authenticationService.register(authRequest);
        } catch (EmailAlreadyExistsException exception) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "E-mailadres is al in gebruik.");
        }
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authRequest) {
        try {
            AuthenticationResponse result = authenticationService.login(authRequest);
            LOGGER.warn("Login successful for user ID (" + result.getId() + ")");
            return result;
        } catch (InvalidCredentialsException exception) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Ongeldige e-mail/wachtwoord combinatie.");
        }
    }
}
