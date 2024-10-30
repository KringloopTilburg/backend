package nl.kringlooptilburg.authenticationservice.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import nl.kringlooptilburg.authenticationservice.exception.EmailAlreadyExistsException;
import nl.kringlooptilburg.authenticationservice.exception.InvalidCredentialsException;
import nl.kringlooptilburg.authenticationservice.model.User;
import nl.kringlooptilburg.authenticationservice.publisher.LogPublisher;
import nl.kringlooptilburg.authenticationservice.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final LogPublisher logPublisher;

    public User save(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            logPublisher.publishLog("User with email: " + user.getEmail() + " already exists.");
            throw new EmailAlreadyExistsException("Email address already exists.");
        }
        return userRepository.save(user);
    }

    public User findByEmailAndPassword(String email, String password) {
        var user = userRepository.findByEmailAndPassword(email, password);
        if (user == null) {
            throw new InvalidCredentialsException("Invalid email or password");
        }
        return user;
    }

    /*
    public User findById(Integer id) throws IllegalAccessException {
        var user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new IllegalAccessException("User not found");
        }
    }

     */

    public User findByEmail(String email) {
        var user = userRepository.findByEmail(email);
        if (user == null) {
            throw new InvalidCredentialsException("Invalid email or password");
        }
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        List<GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority(user.getRole()));
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(), authorities);
    }
}
