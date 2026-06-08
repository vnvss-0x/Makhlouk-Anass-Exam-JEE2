package com.makhlouk.anass.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final String ENCODED_PASSWORD = new BCryptPasswordEncoder().encode("secret1234");

    private static final Map<String, String> USER_ROLES = Map.of(
            "client1",  "ROLE_CLIENT",
            "employe1", "ROLE_EMPLOYE",
            "admin",    "ROLE_ADMIN"
    );

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String role = USER_ROLES.get(username);
        if (role == null) {
            throw new UsernameNotFoundException("Utilisateur non trouvé : " + username);
        }
        return new User(
                username,
                ENCODED_PASSWORD,
                List.of(new SimpleGrantedAuthority(role))
        );
    }
}
