package com.portfolio.myportfolio.service.implementation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.portfolio.myportfolio.dto.LoginRequest;
import com.portfolio.myportfolio.dto.LoginResponse;
import com.portfolio.myportfolio.entity.User;
import com.portfolio.myportfolio.repo.UserRepository;
import com.portfolio.myportfolio.service.AuthService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

  @Value("${web.admin.email}")
  private String userEmail;
  @Value("${web.admin.pass}")
  private String password;

      
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginResponse authenticate(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        CustomUserDetails userDetails = new CustomUserDetails(user);
        String token = jwtService.generateToken(userDetails);
        return new LoginResponse(token, user.getEmail(), user.getId());
    }

    public User createDefaultAdmin() {
        if (userRepository.findByEmail(userEmail).isPresent()) {
            return userRepository.findByEmail(userEmail).get();
        }

        User admin = new User();
        admin.setEmail(userEmail);
        admin.setPassword(passwordEncoder.encode(password));
        admin.setRole(User.Role.ADMIN);
        
        return userRepository.save(admin);
    }
}
