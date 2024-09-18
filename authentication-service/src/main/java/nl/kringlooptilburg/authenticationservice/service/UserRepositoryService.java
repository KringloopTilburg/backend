package nl.kringlooptilburg.authenticationservice.service;

import lombok.RequiredArgsConstructor;
import nl.kringlooptilburg.authenticationservice.exception.EmailAlreadyExistsException;
import nl.kringlooptilburg.authenticationservice.exception.InvalidCredentialsException;
import nl.kringlooptilburg.authenticationservice.model.User;
import nl.kringlooptilburg.authenticationservice.publisher.LogPublisher;
import nl.kringlooptilburg.authenticationservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRepositoryService {

    private final UserRepository userRepository;
    private final LogPublisher logPublisher;


    public User save(User user) {
        if(userRepository.existsByEmail(user.getEmail())) {
            logPublisher.publishLog("User with email: " + user.getEmail() + " already exists.");
            throw new EmailAlreadyExistsException("Email address already exists.");
        }
        var tmp =  userRepository.save(user);
        return tmp;
    }

    public User findByEmailAndPassword(String email, String password){
        var user = userRepository.findByEmailAndPassword(email, password);
        if(user == null) {
            throw new InvalidCredentialsException("Invalid email or password");
        }
        return user;
    }

    public User findById(Integer id) throws IllegalAccessException {
        var user = userRepository.findById(id);
        if(user.isPresent()) {
            return user.get();
        } else {
            throw new IllegalAccessException("User not found");
        }
    }

    public User findByEmail(String email) {
        var user = userRepository.findByEmail(email);
        if(user == null) {
            throw new InvalidCredentialsException("Invalid email or password");
        }
        return user;
    }
}
