package com.fabish.LinkUpAPI.service;

import com.fabish.LinkUpAPI.dto.EmployerAnalyticsDTO;
import com.fabish.LinkUpAPI.dto.InterviewDTO;
import com.fabish.LinkUpAPI.dto.InterviewRequestDTO;
import com.fabish.LinkUpAPI.dto.JobApplicationDTO;
import com.fabish.LinkUpAPI.dto.JobDTO;
import com.fabish.LinkUpAPI.dto.RecommendationDTO;
import com.fabish.LinkUpAPI.entity.Interview;
import com.fabish.LinkUpAPI.entity.Job;
import com.fabish.LinkUpAPI.entity.JobApplication;
import com.fabish.LinkUpAPI.entity.User;
import com.fabish.LinkUpAPI.exception.ResourceNotFoundException;
import com.fabish.LinkUpAPI.exception.UnauthorizedException;
import com.fabish.LinkUpAPI.repository.InterviewRepository;
import com.fabish.LinkUpAPI.repository.JobApplicationRepository;
import com.fabish.LinkUpAPI.repository.JobRepository;
import com.fabish.LinkUpAPI.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployerService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private JobApplicationRepository applicationRepository;
    @Autowired
    private InterviewRepository interviewRepository;
    @Autowired
    private JobMatchingService matchingService;

    public JobDTO createJob(Long employerId, JobDTO jobDTO) {
        User employer = findUser(employerId);
        Job job = new Job();
        job.setTitle(jobDTO.getTitle());
        job.setDescription(jobDTO.getDescription());
        job.setRequirements(jobDTO.getRequirements());
        job.setEmployer(employer);
        job.setPostedDate(LocalDateTime.now());
        job = jobRepository.save(job);
        return mapToJobDTO(job);
    }

    public Page<JobDTO> getEmployerJobs(Long employerId, int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, parseSort(sort));
        return jobRepository.findByEmployerId(employerId, pageable).map(this::mapToJobDTO);
    }

    public JobDTO updateJob(Long employerId, Long jobId, JobDTO jobDTO) {
        Job job = findJob(jobId, employerId);
        job.setTitle(jobDTO.getTitle());
        job.setDescription(jobDTO.getDescription());
        job.setRequirements(jobDTO.getRequirements());
        job = jobRepository.save(job);
        return mapToJobDTO(job);
    }

    public void deleteJob(Long employerId, Long jobId) {
        Job job = findJob(jobId, employerId);
        job.setActive(false);
        jobRepository.save(job);
    }

    public List<JobApplicationDTO> getJobApplications(Long employerId, Long jobId) {
        findJob(jobId, employerId);
        return applicationRepository.findByJobId(jobId)
                .stream().map(this::mapToApplicationDTO).collect(Collectors.toList());
    }

    public List<RecommendationDTO> getCandidateRecommendations(Long employerId, Long jobId) {
        findJob(jobId, employerId);
        return matchingService.getCandidateRecommendations(jobId);
    }

    public InterviewDTO scheduleInterview(Long employerId, Long applicationId, InterviewRequestDTO interviewDTO) {
        JobApplication application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));
        if (!application.getJob().getEmployer().getId().equals(employerId)) {
            throw new UnauthorizedException("Not authorized");
        }
        Interview interview = new Interview();
        interview.setApplication(application);
        interview.setDateTime(interviewDTO.getDateTime());
        interview.setLocation(interviewDTO.getLocation());
        interview = interviewRepository.save(interview);
        return new InterviewDTO(interview.getId(), applicationId, interview.getDateTime(), interview.getLocation());
    }

    public EmployerAnalyticsDTO getAnalytics(Long employerId) {
        List<Job> jobs = jobRepository.findByEmployerId(employerId, Pageable.unpaged()).getContent();
        long totalApplications = jobs.stream()
                .flatMap(job -> applicationRepository.findByJobId(job.getId()).stream())
                .count();
        return new EmployerAnalyticsDTO(jobs.size(), totalApplications, jobs.isEmpty() ? 0 : totalApplications / jobs.size());
    }

    private User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private Job findJob(Long jobId, Long employerId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));
        if (!job.getEmployer().getId().equals(employerId)) {
            throw new UnauthorizedException("Not authorized");
        }
        return job;
    }

    private JobDTO mapToJobDTO(Job job) {
        return new JobDTO(job.getId(), job.getTitle(), job.getDescription(), job.getRequirements(), job.getEmployer().getId(), job.getPostedDate());
    }

    private JobApplicationDTO mapToApplicationDTO(JobApplication application) {
        return new JobApplicationDTO(application.getId(), application.getJob().getId(), application.getCandidate().getId(), application.getResumeUrl(), application.getStatus(), application.getAppliedDate());
    }

    // Custom method to parse sort string
    private Sort parseSort(String sort) {
        if (sort == null || sort.trim().isEmpty()) {
            return Sort.unsorted(); // Default to unsorted if no sort provided
        }

        String[] parts = sort.split(",");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Sort parameter must be in format 'field,direction' (e.g., 'postedDate,desc')");
        }

        String field = parts[0].trim();
        String direction = parts[1].trim().toLowerCase();

        Sort.Direction sortDirection = direction.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return Sort.by(sortDirection, field);
    }
}