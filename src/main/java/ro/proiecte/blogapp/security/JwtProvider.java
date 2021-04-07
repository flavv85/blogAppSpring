package ro.proiecte.blogapp.security;
//class care crea JWT dupa autenficare cu succes

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.Key;

@Service
public class JwtProvider {
    // cream un key unic cu care semnam de fiecare data JWT token
    // generam key la service start-up si o alocam fiecarui token now

    private Key key;

    @PostConstruct
    public void init(){
        key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

    // create a method to sign JTW token, cheia generata va semna tokenul JWT
    // token creat trebui introdus in authService

    public String generateToken(Authentication authentication) {
        User principal = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername()) // value of username
                .signWith(key) // pentru a nu crea un key de fiecare data cand generam un
                // token
                .compact();
    }
}
