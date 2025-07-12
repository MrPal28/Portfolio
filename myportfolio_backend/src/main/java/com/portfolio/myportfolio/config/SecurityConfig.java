package com.portfolio.myportfolio.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import java.util.List;

import com.portfolio.myportfolio.service.implementation.JwtAuthenticationFilter;
import com.portfolio.myportfolio.service.implementation.UserDetailsService;




@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

  @Autowired
  private UserDetailsService userDetailsService;

    @Autowired
  private JwtAuthenticationFilter jwtAuthenticationFilter;

  @Value("${frontend.url}")
  private String frontendUrl;


  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{

    http.cors(Customizer.withDefaults())
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(authorize->authorize
        .requestMatchers("/api/**").permitAll()
                .requestMatchers("/admin/login").permitAll()
                .requestMatchers("/admin/**").authenticated()
                .anyRequest().permitAll()).sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();

      }


      @Bean
      public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
      }

      @Bean
      public CorsFilter corsFilter(){
        return new CorsFilter(corsConfigurationSource());
      }

      private UrlBasedCorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173", "http://localhost:3000" ,"https://08kv4fz1-5173.inc1.devtunnels.ms/",frontendUrl ));
        config.setAllowedMethods(List.of("GET" , "POST" , "PUT" , "DELETE" , "OPTIONS" , "PATCH"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
      }

      @Bean
      public AuthenticationManager authenticationManager()throws Exception{
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authProvider);
      }

}