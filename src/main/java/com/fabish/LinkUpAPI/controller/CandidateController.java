package com.fabish.LinkUpAPI.controller;

import com.fabish.LinkUpAPI.dto.*;
import com.fabish.LinkUpAPI.entity.User;
import com.fabish.LinkUpAPI.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/candidate")
@PreAuthorize("hasRole('CANDIDATE')")
public class CandidateController {
    @Autowired
    private CandidateService candidateService;

    @GetMapping("/profile")
    public ResponseEntity<CandidateProfileDTO> getProfile(Authentication auth) {
        User user = (User) auth.getPrincipal();
        return ResponseEntity.ok(candidateService.getProfile(user.getId()));
    }

    @PutMapping("/profile")
    public ResponseEntity<CandidateProfileDTO> updateProfile(
            @RequestBody CandidateProfileDTO profileDTO, Authentication auth) {
        User user = (User) auth.getPrincipal();
        return ResponseEntity.ok(candidateService.updateProfile(user.getId(), profileDTO));
    }

    @PostMapping("/resume")
    public ResponseEntity<ResumeResponseDTO> uploadResume(
            @RequestParam("resume") MultipartFile resume, Authentication auth) {
        User user = (User) auth.getPrincipal();
        return ResponseEntity.ok(candidateService.uploadResume(user.getId(), resume));
    }

    @GetMapping("/jobs")
    public ResponseEntity<Page<JobDTO>> getJobs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "postedDate,desc") String sort) {
        return ResponseEntity.ok(candidateService.getAvailableJobs(page, size, sort));
    }

    @GetMapping("/recommendations")
    public ResponseEntity<List<RecommendationDTO>> getRecommendations(Authentication auth) {
        User user = (User) auth.getPrincipal();
        return ResponseEntity.ok(candidateService.getJobRecommendations(user.getId()));
    }

    @PostMapping("/apply/{jobId}")
    public ResponseEntity<JobApplicationDTO> applyForJob(
            @PathVariable Long jobId,
            @RequestParam(required = false) MultipartFile resume,
            Authentication auth) {
        User user = (User) auth.getPrincipal();
        return ResponseEntity.ok(candidateService.applyForJob(user.getId(), jobId, resume));
    }

    @GetMapping("/applications")
    public ResponseEntity<List<JobApplicationDTO>> getApplications(Authentication auth) {
        User user = (User) auth.getPrincipal();
        return ResponseEntity.ok(candidateService.getApplications(user.getId()));
    }
}
