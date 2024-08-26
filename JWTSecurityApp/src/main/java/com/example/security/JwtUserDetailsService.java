package com.example.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Value("${jwt.user.name}")
    private String userName;

    @Value("${jwt.user.password}")
    private String password;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (userName.equals(username)) {
            return new User(userName, password, new ArrayList<>());
        } else {
            throw new UsernameNotFoundException(username + " is not found in the records");
        }
    }
}
