package com.portfolio.myportfolio.service.implementation;

import java.io.IOException;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.portfolio.myportfolio.dto.CertificateRequest;
import com.portfolio.myportfolio.dto.CertificateResponse;
import com.portfolio.myportfolio.entity.Certificate;
import com.portfolio.myportfolio.repo.CertificateRepository;
import com.portfolio.myportfolio.service.CertificateService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService{

   private final CertificateRepository repo;
    private final CloudinaryUploader uploader;

    public CertificateResponse create(CertificateRequest dto, MultipartFile file)
            throws IOException {

        CloudinaryUploader.UploadResult res = null;
        if (file != null && !file.isEmpty()) res = uploader.uploadFile(file);

        Certificate c = new Certificate();
        BeanUtils.copyProperties(dto, c);
        if (res != null) {
            c.setFileUrl(res.url());
            c.setFilePublicId(res.publicId());
        }
        return map(repo.save(c));
    }

    @Transactional
    public CertificateResponse update(Long id, CertificateRequest dto, MultipartFile file)
            throws IOException {

        Certificate c = repo.findById(id)
                            .orElseThrow(() -> new RuntimeException("Certificate not found"));
        BeanUtils.copyProperties(dto, c, "id", "fileUrl", "filePublicId");

        if (file != null && !file.isEmpty()) {
            // 1️⃣ clean up the previous file
            if (c.getFilePublicId() != null)
                uploader.deletePdf(c.getFilePublicId());

            // 2️⃣ upload the new file
            var res = uploader.uploadFile(file);
            c.setFileUrl(res.url());
            c.setFilePublicId(res.publicId());
        }
        return map(c);
    }

    public void delete(Long id) throws IOException {
        Certificate c = repo.findById(id)
                            .orElseThrow(() -> new RuntimeException("Certificate not found"));
        if (c.getFilePublicId() != null) uploader.deletePdf(c.getFilePublicId());
        repo.delete(c);
    }

    
    public Page<CertificateResponse> list(Pageable pageable) {
        return repo.findAll(pageable)        
                   .map(this::map);     
    }

    private CertificateResponse map(Certificate certificate) {
        CertificateResponse response = new CertificateResponse();
        BeanUtils.copyProperties(certificate, response);
        return response;
    }
}
