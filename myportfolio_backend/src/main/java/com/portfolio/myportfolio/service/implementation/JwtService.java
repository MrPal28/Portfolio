package com.portfolio.myportfolio.service.implementation;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;
@Service
public class JwtService {

   private Key secretKey;

  public JwtService(@Value("${jwt.secret}") String secret) {
    // secret must be at least 256 bits for HS256 (32+ characters)
    this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
  }

  // public String generateToken(UserDetails userDetails){
  //   Map<String, Object> claims = new HashMap<>();
  //   return createToken(claims,userDetails.getUsername());
  // }

  public String generateToken(UserDetails userDetails) {
    CustomUserDetails customUser = (CustomUserDetails) userDetails;

    Map<String, Object> claims = new HashMap<>();
    claims.put("role", customUser.getRole().name());

    return createToken(claims, userDetails.getUsername());
  }


   private String createToken(Map<String , Object> claims, String subject) {
    return Jwts.builder()
      .setClaims(claims)
      .setSubject(subject)
      .setIssuedAt(new Date(System.currentTimeMillis()))
      .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hrs
      .signWith(secretKey, SignatureAlgorithm.HS256)
      .compact();
  }

  public String extractUsername(String token){
    return extractClaim(token, Claims::getSubject);
  }

  public Date extractExpiration(String token){
    return extractClaim(token, Claims::getExpiration);
  }

  public <T> T extractClaim(String token , Function<Claims , T> claimsResolver){
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public Claims extractAllClaims(String token){
    return Jwts.parser()
    .setSigningKey(secretKey)
    .build()
    .parseClaimsJws(token)
    .getBody();
  }


  private Boolean isTokenExpired(String token){
    return extractExpiration(token).before(new Date());
  }

  public Boolean validateToken(String token , UserDetails userDetails){
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }
  public String extractRole(String token) {
    return extractClaim(token, claims -> claims.get("role").toString());
}
}