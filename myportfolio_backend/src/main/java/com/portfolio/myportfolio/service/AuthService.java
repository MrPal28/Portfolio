package com.portfolio.myportfolio.service;


import com.portfolio.myportfolio.dto.LoginRequest;
import com.portfolio.myportfolio.dto.LoginResponse;
import com.portfolio.myportfolio.entity.User;


public interface AuthService {

    public LoginResponse authenticate(LoginRequest request);
    public User createDefaultAdmin();
}