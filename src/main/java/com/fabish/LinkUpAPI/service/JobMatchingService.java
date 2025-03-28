package com.fabish.LinkUpAPI.service;

import com.fabish.LinkUpAPI.entity.Job;
import com.fabish.LinkUpAPI.entity.Role;
import com.fabish.LinkUpAPI.entity.User;
import com.fabish.LinkUpAPI.repository.JobRepository;
import com.fabish.LinkUpAPI.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobMatchingService {
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private UserRepository userRepository;

    public List<RecommendationDTO> getJobRecommendations(Long candidateId) {
        User candidate = userRepository.findById(candidateId)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found"));
        List<Job> jobs = jobRepository.findByActiveTrue(Pageable.unpaged()).getContent();
        return jobs.stream()
                .map(job -> new RecommendationDTO(job.getId(), job.getTitle(), calculateMatchScore(candidate, job)))
                .filter(dto -> dto.getMatchScore() > 0.7)
                .sorted(Comparator.comparing(RecommendationDTO::getMatchScore).reversed())
                .collect(Collectors.toList());
    }

    public List<RecommendationDTO> getCandidateRecommendations(Long jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));
        List<User> candidates = userRepository.findByRole(Role.CANDIDATE, Pageable.unpaged()).getContent();
        return candidates.stream()
                .map(candidate -> new RecommendationDTO(candidate.getId(), candidate.getName(), calculateMatchScore(candidate, job)))
                .filter(dto -> dto.getMatchScore() > 0.7)
                .sorted(Comparator.comparing(RecommendationDTO::getMatchScore).reversed())
                .collect(Collectors.toList());
    }

    private double calculateMatchScore(User candidate, Job job) {
        // Simple matching logic (can integrate TensorFlow here)
        double score = 0.0;
        if (candidate.getSkills() != null && job.getRequirements() != null) {
            String[] candidateSkills = candidate.getSkills().toLowerCase().split(",");
            String[] jobRequirements = job.getRequirements().toLowerCase().split(",");
            long matches = Arrays.stream(candidateSkills)
                    .filter(skill -> Arrays.asList(jobRequirements).contains(skill.trim()))
                    .count();
            score = (double) matches / jobRequirements.length;
        }
        return score;
    }
}