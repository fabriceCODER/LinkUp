package com.fabish.LinkUpAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employer")
@PreAuthorize("hasRole('EMPLOYER')")
public class EmployerController {
    @Autowired
    private EmployerService employerService;

    @PostMapping("/jobs")
    public ResponseEntity<JobDTO> createJob(
            @RequestBody JobDTO jobDTO, Authentication auth) {
        User user = (User) auth.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(employerService.createJob(user.getId(), jobDTO));
    }

    @GetMapping("/jobs")
    public ResponseEntity<Page<JobDTO>> getJobs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "postedDate,desc") String sort,
            Authentication auth) {
        User user = (User) auth.getPrincipal();
        return ResponseEntity.ok(employerService.getEmployerJobs(user.getId(), page, size, sort));
    }

    @PutMapping("/jobs/{jobId}")
    public ResponseEntity<JobDTO> updateJob(
            @PathVariable Long jobId, @RequestBody JobDTO jobDTO, Authentication auth) {
        User user = (User) auth.getPrincipal();
        return ResponseEntity.ok(employerService.updateJob(user.getId(), jobId, jobDTO));
    }

    @DeleteMapping("/jobs/{jobId}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long jobId, Authentication auth) {
        User user = (User) auth.getPrincipal();
        employerService.deleteJob(user.getId(), jobId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/applications/{jobId}")
    public ResponseEntity<List<JobApplicationDTO>> getApplications(
            @PathVariable Long jobId, Authentication auth) {
        User user = (User) auth.getPrincipal();
        return ResponseEntity.ok(employerService.getJobApplications(user.getId(), jobId));
    }

    @GetMapping("/recommendations/{jobId}")
    public ResponseEntity<List<RecommendationDTO>> getCandidateRecommendations(
            @PathVariable Long jobId, Authentication auth) {
        User user = (User) auth.getPrincipal();
        return ResponseEntity.ok(employerService.getCandidateRecommendations(user.getId(), jobId));
    }

    @PostMapping("/interview/{applicationId}")
    public ResponseEntity<InterviewDTO> scheduleInterview(
            @PathVariable Long applicationId,
            @RequestBody InterviewRequestDTO interviewDTO,
            Authentication auth) {
        User user = (User) auth.getPrincipal();
        return ResponseEntity.ok(employerService.scheduleInterview(user.getId(), applicationId, interviewDTO));
    }

    @GetMapping("/analytics")
    public ResponseEntity<EmployerAnalyticsDTO> getAnalytics(Authentication auth) {
        User user = (User) auth.getPrincipal();
        return ResponseEntity.ok(employerService.getAnalytics(user.getId()));
    }
}