package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Role;
import com.springboot.blog.entity.User;
import com.springboot.blog.exception.BlogApiException;
import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.payload.SignUpDto;
import com.springboot.blog.repository.RoleRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.security.JwtTokenProvider;
import com.springboot.blog.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(
            AuthenticationManager authenticationManager,
            UserRepository        userRepository,
            RoleRepository        roleRepository,
            PasswordEncoder       passwordEncoder,
            JwtTokenProvider      jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository        = userRepository;
        this.roleRepository        = roleRepository;
        this.passwordEncoder       = passwordEncoder;
        this.jwtTokenProvider      = jwtTokenProvider;
    }

    @Override
    public String login(LoginDto loginDto) {
        Authentication auth = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(auth);

        return jwtTokenProvider.generateToken(auth);
    }

    @Override
    public String register(SignUpDto signUpDto) {
        if (userRepository.existsByUsername(signUpDto.getUsername()))
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Username is already exists!");

        if (userRepository.existsByEmail(signUpDto.getEmail()))
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Email is already exists!");

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new BlogApiException(HttpStatus.BAD_REQUEST, "User role not set."));
        roles.add(userRole);

        userRepository.save(User.builder()
                .username(signUpDto.getUsername())
                .email(signUpDto.getEmail())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .roles(roles)
                .build());

        return "User registered successfully!";
    }
}
