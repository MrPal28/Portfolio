package com.portfolio.myportfolio.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProjectDto {
  private String title;
  private String overview;
  private String description;
  private List<String> technologies;
  private LocalDate starDate;
  private LocalDate endDate;
  private boolean isTeamProj;
  private String gitHubUrl; 
}
