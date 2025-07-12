package com.portfolio.myportfolio.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portfolio.myportfolio.entity.User;

public interface UserRepository extends JpaRepository<User , Long> {
   Optional<User> findByEmail(String email);
}
