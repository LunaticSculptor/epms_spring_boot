package com.example.epms.domain.service;

import com.example.epms.domain.User;
import com.example.epms.domain.model.UserPrincipal;
import com.example.epms.domain.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialException;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(()->new BadCredentialsException("Invalid Credentials"));
        if (user == null) {
            System.out.println("User not found");
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return new UserPrincipal(user);
    }
}
