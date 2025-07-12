package com.portfolio.myportfolio.dto;

import java.time.LocalDate;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CertificateResponse {
   Long id;
        String title;
        String issuer;
        LocalDate issueDate;
        String credentialId;
        String credentialUrl;
        String description;
        String fileUrl;
        @Column(length = 200)
 String filePublicId;
}
