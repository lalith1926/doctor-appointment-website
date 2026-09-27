package com.doctor.website.service;

import com.doctor.website.DTO.AuthResponse;

import com.doctor.website.DTO.LoginRequest;
import com.doctor.website.DTO.RegisterRequest;
import com.doctor.website.DTO.UserResponse;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
    
    UserResponse getCurrentUser(String email);

}