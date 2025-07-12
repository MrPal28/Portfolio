package com.portfolio.myportfolio.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.portfolio.myportfolio.entity.Project;
import com.portfolio.myportfolio.repo.ProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    // Public endpoints
    public Page<Project> getPublishedProjects(Pageable pageable) {
        return projectRepository.findByIsPublishedTrueOrderByCreatedAtDesc(pageable);
    }

    public Optional<Project> getPublishedProjectById(Long id) {
        return projectRepository.findById(id)
                .filter(Project::getIsPublished);
    }

    public List<String> getAllTechnologies() {
        return projectRepository.findDistinctTechnologiesByIsPublishedTrue();
    }

    public Page<Project> getPublishedProjectsByType(Boolean isTeamProject, Pageable pageable) {
        return projectRepository.findByIsPublishedTrueAndIsTeamProjectOrderByCreatedAtDesc(isTeamProject, pageable);
    }

    // Admin endpoints
    public Page<Project> getAllProjects(Pageable pageable) {
        return projectRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }

    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    public Project updateProject(Long id, Project projectDetails) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        project.setTitle(projectDetails.getTitle());
        project.setOverview(projectDetails.getOverview());
        project.setDescription(projectDetails.getDescription());
        project.setTechnologies(projectDetails.getTechnologies());
        project.setStartDate(projectDetails.getStartDate());
        project.setEndDate(projectDetails.getEndDate());
        project.setIsTeamProject(projectDetails.getIsTeamProject());
        project.setGithubUrl(projectDetails.getGithubUrl());

        return projectRepository.save(project);
    }

    public void deleteProject(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new RuntimeException("Project not found");
        }
        projectRepository.deleteById(id);
    }

    public Project togglePublishStatus(Long id, Boolean publishStatus) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        project.setIsPublished(publishStatus);
        return projectRepository.save(project);
    }
}
