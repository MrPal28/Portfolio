package com.portfolio.myportfolio.service.implementation;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CloudinaryUploader {

    private final Cloudinary cloudinary;

   public UploadResult uploadFile(MultipartFile file) throws IOException {
        String cleanName = Paths.get(file.getOriginalFilename())
                                .getFileName().toString();
        Map<?, ?> raw = cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.asMap(
                        "resource_type", "raw",   // PDF/ZIP/any binary ⇒ raw
                        "type",          "upload",
                        "public_id",     "resumes/" + cleanName, 
                        "overwrite",     true,
                        "invalidate",     true    // bust old CDN copies if name collides
                )
        );
         return new UploadResult(
            raw.get("secure_url").toString(),
            raw.get("public_id").toString(),
            (Integer) raw.getOrDefault("pages", null) // only for multi-page PDFs
        );
    }

    
 public void deletePdf(String publicId) throws IOException {
        cloudinary.uploader().destroy(
            publicId,
            ObjectUtils.asMap(
                "resource_type", "raw",
                "invalidate", true
            )
        );
    }



    /* Tiny DTO */
     public record UploadResult(String url, String publicId, Integer pages) {}
}