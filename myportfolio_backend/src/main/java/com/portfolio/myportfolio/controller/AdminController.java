package com.portfolio.myportfolio.controller;

import jakarta.validation.Valid;

import java.io.IOException;
import java.nio.file.NoSuchFileException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.portfolio.myportfolio.dto.CertificateRequest;
import com.portfolio.myportfolio.dto.CertificateResponse;
import com.portfolio.myportfolio.dto.LoginRequest;
import com.portfolio.myportfolio.dto.LoginResponse;
import com.portfolio.myportfolio.dto.ResumeResponse;
import com.portfolio.myportfolio.entity.Blog;
import com.portfolio.myportfolio.entity.ContactMessage;
import com.portfolio.myportfolio.entity.Project;
import com.portfolio.myportfolio.service.AuthService;
import com.portfolio.myportfolio.service.BlogService;
import com.portfolio.myportfolio.service.CertificateService;
import com.portfolio.myportfolio.service.ContactService;
import com.portfolio.myportfolio.service.implementation.ProjectService;
import com.portfolio.myportfolio.service.implementation.ResumeService;


@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AuthService authService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private ContactService contactService;

    @Autowired
     private  CertificateService certificateService;

     @Autowired
     private ResumeService resumeService;

    // Authentication
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        try {
            LoginResponse response = authService.authenticate(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Project management
    @GetMapping("/projects")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<Project>> getAllProjects(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Project> projects = projectService.getAllProjects(pageable);
        return ResponseEntity.ok(projects);
    }

    @PostMapping("/projects")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Project> createProject(@Valid @RequestBody Project project) {
        Project createdProject = projectService.createProject(project);
        return ResponseEntity.ok(createdProject);
    }

    @PutMapping("/projects/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @Valid @RequestBody Project project) {
        try {
            Project updatedProject = projectService.updateProject(id, project);
            return ResponseEntity.ok(updatedProject);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/projects/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        try {
            projectService.deleteProject(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/projects/{id}/publish")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Project> togglePublishStatus(@PathVariable Long id, @RequestParam Boolean state) {
        try {
            Project project = projectService.togglePublishStatus(id, state);
            return ResponseEntity.ok(project);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Blog management
    @PostMapping("/blogs")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Blog> createBlog(@Valid @RequestBody Blog blog) {
        Blog createdBlog = blogService.createBlog(blog);
        return ResponseEntity.ok(createdBlog);
    }

    @PutMapping("/blogs/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Blog> updateBlog(@PathVariable Long id, @Valid @RequestBody Blog blog) {
        try {
            Blog updatedBlog = blogService.updateBlog(id, blog);
            return ResponseEntity.ok(updatedBlog);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/blogs")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<Blog>> getAllBlogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Blog> blog = blogService.getAllBlogs(pageable);
        return ResponseEntity.ok(blog);
    }

    @DeleteMapping("/blogs/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteBlog(@PathVariable Long id) {
        try {
            blogService.deleteBlog(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /* ---------- ADMIN: upload / replace ---------- */
    @PostMapping("/resume")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResumeResponse> upload(
            @RequestParam("file") MultipartFile file) {
        try {
            return ResponseEntity.ok(resumeService.upload(file));
        } catch (IllegalArgumentException ex) { // empty file
            return ResponseEntity.badRequest().body(null);
        } catch (IOException ex) { // Cloudinary / IO failure
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @GetMapping("/resume")
    
    public ResponseEntity<String> getResumeUrl() {
        try {
            return ResponseEntity.ok(resumeService.getUrl());
        } catch (NoSuchFileException ex) {
            return ResponseEntity.notFound().build();
        } catch (IOException ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/resume")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete() {
        try {
            resumeService.delete();
            return ResponseEntity.noContent().build();
        } catch (IOException ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Messages
    @GetMapping("/messages")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<ContactMessage>> getAllMessages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ContactMessage> messages = contactService.getAllMessages(pageable);
        return ResponseEntity.ok(messages);
    }

    @PatchMapping("/messages/{id}/read")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ContactMessage> markMessageAsRead(@PathVariable Long id) {
        try {
            ContactMessage message = contactService.markAsRead(id);
            return ResponseEntity.ok(message);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/messages/unread-count")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> getUnreadCount() {
        long count = contactService.getUnreadCount();
        return ResponseEntity.ok(count);
    }

    @DeleteMapping("/message/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id){
          try {
             contactService.deleteMessage(id);
            return ResponseEntity.ok(null);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

     
    @PostMapping(value = "/certificate/add",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public CertificateResponse create(
            @RequestPart("meta")  @Valid CertificateRequest meta,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) throws Exception {
        return certificateService.create(meta, file);
    }

    
    @GetMapping("/certificate")
    public Page<CertificateResponse> list(
            @RequestParam(defaultValue = "0")   int  page,
            @RequestParam(defaultValue = "10")  int  size,
            @RequestParam(defaultValue = "issueDate") String sortBy,
            @RequestParam(defaultValue = "desc")      String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase("asc")
                    ? Sort.by(sortBy).ascending()
                    : Sort.by(sortBy).descending();
        return certificateService.list(PageRequest.of(page, size, sort));
    }

    
    @PutMapping(value = "/certificate/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CertificateResponse update(
            @PathVariable Long id,
            @RequestPart("meta")  @Valid CertificateRequest meta,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) throws Exception {
        return certificateService.update(id, meta, file);
    }

    
    @DeleteMapping("/certificate/{id}")
    public void delete(@PathVariable Long id) throws Exception {
        certificateService.delete(id);
    }
}