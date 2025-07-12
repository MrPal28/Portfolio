package com.portfolio.myportfolio.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResumeResponse {
  private String url;
  private LocalDateTime updatedAt;
}
