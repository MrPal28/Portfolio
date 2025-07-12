package com.portfolio.myportfolio.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "certificates")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Certificate {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)          
    private String title;        
    @Column(nullable = false)          
    private String issuer;       
    @Column(nullable = false)          
    private LocalDate issueDate; // 2025‑03‑10
                         

    private String credentialId;      
    private String credentialUrl;      
    private String fileUrl;            

    @ElementCollection
    @CollectionTable(name = "project_technologies", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "technology")
    private List<String> technologies;

    @Column(length = 200)
    private String filePublicId;
}