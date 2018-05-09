package io.engi.receipts.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class DummyUserDetails implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.equals("user"))
            return User.withUsername("user")
                    .password("{noop}qwerty")
                    .authorities("ROLE_USER")
                    .roles("USER")
                    .build();
        if (username.equals("debug"))
            return User.withUsername("debug")
                    .password("{noop}debug")
                    .authorities("ROLE_USER")
                    .roles("USER")
                    .build();
        throw new UsernameNotFoundException(String.format("No user found with username '%s'", username));
    }


}
