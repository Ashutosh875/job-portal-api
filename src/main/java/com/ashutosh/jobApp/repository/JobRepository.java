package com.ashutosh.jobApp.repository;

import com.ashutosh.jobApp.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findAllByCompanyId(Long companyId);
}
