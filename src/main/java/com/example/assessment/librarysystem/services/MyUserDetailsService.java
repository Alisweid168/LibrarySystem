package com.example.assessment.librarysystem.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Load user from the database, for now returning a dummy user
        return org.springframework.security.core.userdetails.User
                .withUsername("admin")
                .password("{noop}password") // NoOpPasswordEncoder for simplicity
                .roles("USER")
                .build();
    }
}