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
import ro.proiecte.blogapp.security.JwtProvider;

import java.util.Optional;

// Mapam RegisterRequest obj la User obj; cand setam parola chemam metoda encodePassword
@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtProvider jwtProvider;

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

    public String login(LoginRequest loginRequest) {
        // sunt siguri ca userul este autentificat dupa ce executam aceasta metoda
        Authentication authenticate =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                        loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        return jwtProvider.generateToken(authenticate);
        // trimitem tokenul in controller care il va trimite catre client
    }

    public Optional<org.springframework.security.core.userdetails.User> getCurrentUser(){
        org.springframework.security.core.userdetails.User principal =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Optional.of(principal);
    }

}
