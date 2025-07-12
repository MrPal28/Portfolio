package com.portfolio.myportfolio.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CertificateRequest {
        String title;
        String issuer;
        @JsonFormat(pattern="yyyy-MM-dd") LocalDate issueDate;
  
        String credentialId;
        String credentialUrl;
        String description;
}
