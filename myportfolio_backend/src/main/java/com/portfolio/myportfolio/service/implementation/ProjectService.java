package com.portfolio.myportfolio.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.portfolio.myportfolio.entity.Project;


public interface ProjectService {

   

    // Public endpoints
    public Page<Project> getPublishedProjects(Pageable pageable);

    public Optional<Project> getPublishedProjectById(Long id);

    public List<String> getAllTechnologies();

    public Page<Project> getPublishedProjectsByType(Boolean isTeamProject, Pageable pageable);

    // Admin endpoints
    public Page<Project> getAllProjects(Pageable pageable);
    public Optional<Project> getProjectById(Long id);

    public Project createProject(Project project);

    public Project updateProject(Long id, Project projectDetails);
    public void deleteProject(Long id);

    public Project togglePublishStatus(Long id, Boolean publishStatus);
}