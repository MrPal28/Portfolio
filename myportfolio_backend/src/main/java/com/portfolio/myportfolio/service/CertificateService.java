package com.portfolio.myportfolio.service;

import java.io.IOException;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.web.multipart.MultipartFile;

import com.portfolio.myportfolio.dto.CertificateRequest;
import com.portfolio.myportfolio.dto.CertificateResponse;



public interface CertificateService {



    public CertificateResponse create(CertificateRequest dto, MultipartFile file)
            throws IOException;

    public CertificateResponse update(Long id, CertificateRequest dto, MultipartFile file)
            throws IOException ;

    public void delete(Long id) throws IOException ;
    
    public Page<CertificateResponse> list(Pageable pageable);
}