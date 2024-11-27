package nl.kringlooptilburg.authenticationservice.controller;

import com.google.api.client.json.Json;
import com.google.api.client.json.JsonFactory;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import nl.kringlooptilburg.authenticationservice.exception.EmailAlreadyExistsException;
import nl.kringlooptilburg.authenticationservice.exception.InvalidCredentialsException;
import nl.kringlooptilburg.authenticationservice.model.AuthenticationRequest;
import nl.kringlooptilburg.authenticationservice.model.AuthenticationResponse;
import nl.kringlooptilburg.authenticationservice.service.AuthenticationService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;
    private static final Log LOGGER = LogFactory.getLog(AuthController.class); // google cloud logging


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
            ResponseEntity<Object> result = ResponseEntity.ok(authenticationService.login(authRequest));
            LOGGER.warn("Login Succes: " + result.getStatusCode() + " " + result.getBody());
            return result;
        } catch (InvalidCredentialsException exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Ongeldige e-mail/wachtwoord combinatie.");
        }
    }
}
