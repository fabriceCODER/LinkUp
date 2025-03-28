package com.fabish.LinkUpAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AiConfigDTO {
    private double matchingThreshold;
    private double weightSkills;
    private double weightExperience;
}