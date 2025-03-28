package com.fabish.LinkUpAPI.service;

import com.fabish.LinkUpAPI.dto.*;
import com.fabish.LinkUpAPI.entity.Job;
import com.fabish.LinkUpAPI.entity.JobApplication;
import com.fabish.LinkUpAPI.entity.User;
import com.fabish.LinkUpAPI.exception.ResourceNotFoundException;
import com.fabish.LinkUpAPI.repository.JobApplicationRepository;
import com.fabish.LinkUpAPI.repository.JobRepository;
import com.fabish.LinkUpAPI.repository.UserRepository;
import com.fabish.LinkUpAPI.security.SortUtil; // Adjusted to match your package
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CandidateService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private JobApplicationRepository applicationRepository;
    @Autowired
    private JobMatchingService matchingService;

    public CandidateProfileDTO getProfile(Long candidateId) {
        User candidate = findUser(candidateId);
        return new CandidateProfileDTO(candidate.getId(), candidate.getName(), candidate.getSkills(), candidate.getResumeUrl());
    }

    public CandidateProfileDTO updateProfile(Long candidateId, CandidateProfileDTO profileDTO) {
        User candidate = findUser(candidateId);
        candidate.setName(profileDTO.getName());
        candidate.setSkills(profileDTO.getSkills());
        userRepository.save(candidate);
        return profileDTO;
    }

    public ResumeResponseDTO uploadResume(Long candidateId, MultipartFile resume) {
        User candidate = findUser(candidateId);
        String resumeUrl = saveResumeFile(resume);
        candidate.setResumeUrl(resumeUrl);
        userRepository.save(candidate);
        return new ResumeResponseDTO(resumeUrl);
    }

    public Page<JobDTO> getAvailableJobs(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, SortUtil.parseSort(sort));
        return jobRepository.findByActiveTrue(pageable).map(this::mapToJobDTO);
    }

    public List<RecommendationDTO> getJobRecommendations(Long candidateId) {
        return matchingService.getJobRecommendations(candidateId);
    }

    public JobApplicationDTO applyForJob(Long candidateId, Long jobId, MultipartFile resume) {
        User candidate = findUser(candidateId);
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));
        JobApplication application = new JobApplication();
        application.setJob(job);
        application.setCandidate(candidate);
        application.setResumeUrl(resume != null ? saveResumeFile(resume) : candidate.getResumeUrl());
        application.setAppliedDate(LocalDateTime.now());
        application = applicationRepository.save(application);
        return mapToApplicationDTO(application);
    }

    public List<JobApplicationDTO> getApplications(Long candidateId) {
        return applicationRepository.findByCandidateId(candidateId)
                .stream().map(this::mapToApplicationDTO).collect(Collectors.toList());
    }

    private User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private JobDTO mapToJobDTO(Job job) {
        return new JobDTO(job.getId(), job.getTitle(), job.getDescription(), job.getRequirements(), job.getEmployer().getId(), job.getPostedDate());
    }

    private JobApplicationDTO mapToApplicationDTO(JobApplication application) {
        return new JobApplicationDTO(application.getId(), application.getJob().getId(), application.getCandidate().getId(), application.getResumeUrl(), application.getStatus(), application.getAppliedDate());
    }

    private String saveResumeFile(MultipartFile file) {
        // Implement file storage (e.g., local filesystem, S3)
        return "resume_url_placeholder";
    }
}