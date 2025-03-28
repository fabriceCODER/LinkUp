package com.fabish.LinkUpAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlatformAnalyticsDTO {
    private long totalUsers;
    private long totalJobs;
    private long totalApplications;
    private long activeUsers;
}