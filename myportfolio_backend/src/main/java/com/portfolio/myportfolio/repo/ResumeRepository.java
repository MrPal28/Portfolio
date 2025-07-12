package com.portfolio.myportfolio.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portfolio.myportfolio.entity.ResumeDoc;

public interface ResumeRepository extends JpaRepository<ResumeDoc, Long> {
    Optional<ResumeDoc> findFirstByOrderByIdAsc();
}
