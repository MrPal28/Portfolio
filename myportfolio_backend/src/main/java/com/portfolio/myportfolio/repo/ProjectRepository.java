package com.portfolio.myportfolio.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.portfolio.myportfolio.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project , Long> {
  /* PUBLIC QUERIES */
   Page<Project> findByIsPublishedTrueOrderByCreatedAtDesc(Pageable pageable);
    
    Page<Project> findByIsPublishedTrueAndIsTeamProjectOrderByCreatedAtDesc(Boolean isTeamProject, Pageable pageable);
    
    @Query("SELECT DISTINCT t FROM Project p JOIN p.technologies t WHERE p.isPublished = true")
    List<String> findDistinctTechnologiesByIsPublishedTrue();
    
    Page<Project> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
