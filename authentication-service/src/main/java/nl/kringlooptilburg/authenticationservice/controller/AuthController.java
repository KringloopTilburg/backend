package nl.kringlooptilburg.authenticationservice.controller;

import lombok.RequiredArgsConstructor;
import nl.kringlooptilburg.authenticationservice.exception.EmailAlreadyExistsException;
import nl.kringlooptilburg.authenticationservice.exception.InvalidCredentialsException;
import nl.kringlooptilburg.authenticationservice.model.AuthenticationRequest;
import nl.kringlooptilburg.authenticationservice.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody AuthenticationRequest authRequest) {
        try {
            return ResponseEntity.ok(authenticationService.register(authRequest));
        } catch (EmailAlreadyExistsException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email bestaat al");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody AuthenticationRequest authRequest) {
        try {
            return ResponseEntity.ok(authenticationService.login(authRequest));
        } catch (InvalidCredentialsException exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Ongeldige e-mail/wachtwoord combinatie.");
        }
    }
}
