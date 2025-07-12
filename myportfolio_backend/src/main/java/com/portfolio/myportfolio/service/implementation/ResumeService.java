package com.portfolio.myportfolio.service.implementation;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.portfolio.myportfolio.dto.ResumeResponse;
import com.portfolio.myportfolio.entity.ResumeDoc;
import com.portfolio.myportfolio.repo.ResumeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ResumeService {

    private final ResumeRepository   repo;
    private final CloudinaryUploader uploader;   

    public ResumeResponse upload(MultipartFile file) throws IOException {
        if (file.isEmpty()) throw new IllegalArgumentException("File is empty");

        // 1️⃣ upload first (so we never lose the résumé if Cloudinary fails)
        var res = uploader.uploadFile(file);

        // 2️⃣ fetch or create DB row
        ResumeDoc doc = repo.findFirstByOrderByIdAsc().orElse(new ResumeDoc());

        // 3️⃣ delete old copy (if any) AFTER new one is safe
        if (doc.getFilePublicId() != null) {
            uploader.deletePdf(doc.getFilePublicId());
        }

        // 4️⃣ save metadata
        doc.setFileUrl(res.url());
        doc.setFilePublicId(res.publicId());
        doc.setUpdatedAt(LocalDateTime.now());
        repo.save(doc);

        return new ResumeResponse(doc.getFileUrl(), doc.getUpdatedAt());
    }

      @Transactional(readOnly = true)
    public String getUrl() throws IOException {
        return repo.findFirstByOrderByIdAsc()
                   .orElseThrow(() -> new NoSuchFileException("Resume not found"))
                   .getFileUrl();
    }

    
     public void delete() throws IOException {
        ResumeDoc doc = repo.findFirstByOrderByIdAsc()
                            .orElseThrow(() -> new NoSuchFileException("Resume not found"));
        uploader.deletePdf(doc.getFilePublicId());
        repo.delete(doc);
    }
}