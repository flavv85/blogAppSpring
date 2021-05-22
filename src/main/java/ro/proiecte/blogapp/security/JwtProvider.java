package ro.proiecte.blogapp.security;
//class care crea JWT dupa autenficare cu succes

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import ro.proiecte.blogapp.exception.SpringBlogException;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

@Service
public class JwtProvider {
// cream un key unic cu care semnam de fiecare data JWT token
// generam key la service start-up si o alocam fiecarui token now

// refactorizare autentificare cu Java Keystores folosing Public si Private keys pentru a nu pierde JWT generat
// daca se resteaza serverul (acesta se va pierde) si userul nu va mai avea acces.
    private KeyStore keyStore;

    @PostConstruct
    public void init() {
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("/springblog.jks");
            keyStore.load(resourceAsStream, "123456".toCharArray());
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new SpringBlogException("Exception occured while loading keystore");
        }

    }
// create a method to sign JTW token, cheia generata va semna tokenul JWT
// token creat trebui introdus in authService

    public String generateToken(Authentication authentication) {
        User principal = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername()) // value of username
                // cream o metoda sa retrieve private key din keystore
                .signWith(getPrivateKey()) // pentru a nu crea un key de fiecare data cand generam un
                // token
                .compact();
    }

    private PrivateKey getPrivateKey() {
// facem cast pentru ca getKey sa returneze in PrivateKey
        try {
            return (PrivateKey) keyStore.getKey("springblog", "123456".toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new SpringBlogException("Exception occured while retrieving public key from keystore");
        }
    }
// validare token
    public boolean validateToken(String jwt) {
        Jwts.parser().setSigningKey(getPublickey()).parseClaimsJws(jwt);
        return true;
    }
// ca sa obtinem publicKey: retrieve certificate
    private PublicKey getPublickey() {
        try {
            return keyStore.getCertificate("springblog").getPublicKey();
        } catch (KeyStoreException e) {
            throw new SpringBlogException("Exception occured while retrieving public key from keystore");
        }
    }

    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(getPublickey())
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}
