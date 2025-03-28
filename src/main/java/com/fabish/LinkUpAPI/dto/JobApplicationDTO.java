package com.fabish.LinkUpAPI.dto;

import com.fabish.LinkUpAPI.entity.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobApplicationDTO {
    private Long id;
    private Long jobId;
    private Long candidateId;
    private String resumeUrl;
    private ApplicationStatus status;
    private LocalDateTime appliedDate;
}