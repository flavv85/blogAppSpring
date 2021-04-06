package ro.proiecte.blogapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.proiecte.blogapp.dto.LoginRequest;
import ro.proiecte.blogapp.dto.RegisterRequest;
import ro.proiecte.blogapp.model.User;
import ro.proiecte.blogapp.repository.UserRepository;

// Mapam RegisterRequest obj la User obj; cand setam parola chemam metoda encodePassword
@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setUserName(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encodePassword(registerRequest.getPassword()));

        // salvam obiectul User in DB
        userRepository.save(user);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public void login(LoginRequest loginRequest) {
        // sunt siguri ca userul este autentificat dupa ce executam aceasta metoda
        Authentication authenticate =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                        loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
    }
}
