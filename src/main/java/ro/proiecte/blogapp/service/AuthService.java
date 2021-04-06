package ro.proiecte.blogapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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

    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setUserName(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(registerRequest.getPassword());

        // salvam obiectul de User in DB
        userRepository.save(user);
    }

    private String encodePassword(String password){
        return passwordEncoder.encode(password);
    }

}
