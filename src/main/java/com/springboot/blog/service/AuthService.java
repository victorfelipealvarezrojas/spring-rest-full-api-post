package com.springboot.blog.service;

import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.payload.SignUpDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(SignUpDto signUpDto);
}
