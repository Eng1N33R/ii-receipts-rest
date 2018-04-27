package io.engi.receipts.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class DummyUserDetails implements UserDetailsService {
    private final UserDetails user =
            User.withUsername("user")
                    .password("qwerty")
                    .roles("USER")
                    .build();

    private final UserDetails user2 =
            User.withUsername("debug")
                    .password("debug")
                    .roles("USER")
                    .build();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.equals("user"))
            return user;
        if (username.equals("debug"))
            return user2;
        throw new UsernameNotFoundException(String.format("No user found with username '%s'", username));
    }
}
