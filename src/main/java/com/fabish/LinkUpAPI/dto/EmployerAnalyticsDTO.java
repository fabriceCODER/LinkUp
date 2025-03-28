package com.fabish.LinkUpAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployerAnalyticsDTO {
    private long totalJobs;
    private long totalApplications;
    private double avgApplicationsPerJob;
}
