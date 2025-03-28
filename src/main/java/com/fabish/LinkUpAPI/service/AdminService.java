package com.fabish.LinkUpAPI.service;

import com.fabish.LinkUpAPI.dto.AiConfigDTO;
import com.fabish.LinkUpAPI.dto.JobDTO;
import com.fabish.LinkUpAPI.dto.PlatformAnalyticsDTO;
import com.fabish.LinkUpAPI.dto.UserDTO;
import com.fabish.LinkUpAPI.entity.Job;
import com.fabish.LinkUpAPI.entity.Role;
import com.fabish.LinkUpAPI.entity.User;
import com.fabish.LinkUpAPI.exception.ResourceNotFoundException;
import com.fabish.LinkUpAPI.repository.JobApplicationRepository;
import com.fabish.LinkUpAPI.repository.JobRepository;
import com.fabish.LinkUpAPI.repository.UserRepository;
import com.fabish.LinkUpAPI.security.SortUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort; // Added import for Sort
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private JobApplicationRepository applicationRepository;

    public Page<UserDTO> getUsers(int page, int size, String role) {
        Pageable pageable = PageRequest.of(page, size);
        if (role != null) {
            return userRepository.findByRole(Role.valueOf(role.toUpperCase()), pageable).map(this::mapToUserDTO);
        }
        return userRepository.findAll(pageable).map(this::mapToUserDTO);
    }

    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepository.delete(user);
    }

    public Page<JobDTO> getAllJobs(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, SortUtil.parseSort(sort));
        return jobRepository.findAll(pageable).map(this::mapToJobDTO);
    }

    public PlatformAnalyticsDTO getPlatformAnalytics() {
        long totalUsers = userRepository.count();
        long totalJobs = jobRepository.count();
        long totalApplications = applicationRepository.count();
        long activeUsers = userRepository.findAll().stream().filter(u -> u.getRole() != Role.ADMIN).count();
        return new PlatformAnalyticsDTO(totalUsers, totalJobs, totalApplications, activeUsers);
    }

    public AiConfigDTO updateAiConfig(AiConfigDTO configDTO) {
        // Implement AI config update logic (e.g., store in DB or config file)
        return configDTO;
    }

    private UserDTO mapToUserDTO(User user) {
        return new UserDTO(user.getId(), user.getEmail(), null, user.getName(), user.getRole().name());
    }

    private JobDTO mapToJobDTO(Job job) {
        return new JobDTO(job.getId(), job.getTitle(), job.getDescription(), job.getRequirements(), job.getEmployer().getId(), job.getPostedDate());
    }
}