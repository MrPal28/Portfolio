package com.portfolio.myportfolio.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portfolio.myportfolio.entity.Certificate;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {}