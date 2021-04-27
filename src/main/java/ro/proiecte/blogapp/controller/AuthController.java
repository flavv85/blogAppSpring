package ro.proiecte.blogapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.proiecte.blogapp.dto.LoginRequest;
import ro.proiecte.blogapp.dto.RegisterRequest;
import ro.proiecte.blogapp.service.AuthService;
import ro.proiecte.blogapp.service.AuthenticationResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // folosim @RequestBody pentru a sugera ca acesta este corpul mesajului
    @PostMapping("/signup")
    // folosim @ResponseEntity pentru ca backend-ul sa primeasca un raspuns de la client
    public ResponseEntity signup(@RequestBody RegisterRequest registerRequest) {
        authService.signup(registerRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest){
        return authService.login(loginRequest);
    }
//    public String login(@RequestBody LoginRequest loginRequest) {
//        return authService.login(loginRequest);
//    }

}
