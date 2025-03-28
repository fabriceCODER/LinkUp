package com.fabish.LinkUpAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationDTO {
    private Long id; // Job ID or Candidate ID depending on context
    private String name; // Job title or Candidate name
    private double matchScore;
}