package com.portfolio.myportfolio.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.portfolio.myportfolio.dto.ContactRequest;
import com.portfolio.myportfolio.entity.Blog;
import com.portfolio.myportfolio.entity.Project;
import com.portfolio.myportfolio.service.BlogService;
import com.portfolio.myportfolio.service.ContactService;
import com.portfolio.myportfolio.service.implementation.ProjectService;
import com.portfolio.myportfolio.service.implementation.ResumeService;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class PublicController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private ResumeService resumeService;

    // Project endpoints
    @GetMapping("/projects")
    public ResponseEntity<Page<Project>> getProjects(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Project> projects = projectService.getPublishedProjects(pageable);
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/projects/{id}")
    public ResponseEntity<Project> getProject(@PathVariable Long id) {
        return projectService.getPublishedProjectById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/projects/tags")
    public ResponseEntity<List<String>> getProjectTags() {
        List<String> tags = projectService.getAllTechnologies();
        return ResponseEntity.ok(tags);
    }

    @GetMapping("/projects/type/{type}")
    public ResponseEntity<Page<Project>> getProjectsByType(
            @PathVariable String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        
        Boolean isTeamProject;
        if ("team".equalsIgnoreCase(type)) {
            isTeamProject = true;
        } else if ("solo".equalsIgnoreCase(type)) {
            isTeamProject = false;
        } else {
            return ResponseEntity.badRequest().build();
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Project> projects = projectService.getPublishedProjectsByType(isTeamProject, pageable);
        return ResponseEntity.ok(projects);
    }

    // Blog endpoints
    @GetMapping("/blogs")
    public ResponseEntity<Page<Blog>> getBlogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Blog> blogs = blogService.getAllBlogs(pageable);
        return ResponseEntity.ok(blogs);
    }

    @GetMapping("/blogs/{id}")
    public ResponseEntity<Blog> getBlog(@PathVariable Long id) {
        return blogService.getBlogById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Contact endpoint
    @PostMapping("/contact")
    public ResponseEntity<String> submitContact(@Valid @RequestBody ContactRequest request) {
        contactService.processContactMessage(request);
        return ResponseEntity.ok("Message sent successfully");
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
}