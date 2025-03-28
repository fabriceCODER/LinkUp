package com.fabish.LinkUpAPI.repository;

import com.fabish.LinkUpAPI.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    Page<Job> findByEmployerId(Long employerId, Pageable pageable);
    Page<Job> findByActiveTrue(Pageable pageable);
}