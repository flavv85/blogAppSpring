package ro.proiecte.blogapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.proiecte.blogapp.model.User;
import ro.proiecte.blogapp.repository.UserRepository;

import java.util.Collection;
import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    // query DB bazat pe username si apoi retrimite user details catre Spring
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // own method -> nu exista in JPA Repo
        User user = userRepository.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("User not " +
                "found: " + username));
        // folosim fully classified class pentru ca deja mai avem o clasa care se numeste tot User
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), true,
                true, true, true, getAuthorities("ROLE_USER"));
    }
    // ne asiguram ca Spring foloseste acest serviciu pentru authentificare
    private Collection<? extends GrantedAuthority> getAuthorities(String role_user) {
        return Collections.singletonList(new SimpleGrantedAuthority(role_user));
    }


}
