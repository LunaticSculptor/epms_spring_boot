package com.example.epms.domain.service;

import com.example.epms.domain.model.UserLogin;
import com.example.epms.domain.User;
import com.example.epms.domain.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
public class UsersService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtService jwtService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public String verify(UserLogin userLogin, String rawPassword) {
        User user = userRepo.findByEmail(userLogin.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("No such user exists. Check your email"));
//        System.out.println("User"+user.getEmail());
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), rawPassword)
            );

            if (authentication.isAuthenticated()) {
                return jwtService.generateToken(user.getEmail(), user.getRole(), user.getUserId());
            } else {
                throw new BadCredentialsException("Wrong password");
            }
        } catch (Exception e) {
            System.out.println("Error"+ Arrays.toString(e.getStackTrace()));
            throw new BadCredentialsException("Invalid email or password");
        }
    }
}
