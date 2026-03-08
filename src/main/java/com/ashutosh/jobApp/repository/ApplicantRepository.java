package com.ashutosh.jobApp.repository;

import com.ashutosh.jobApp.entity.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicantRepository extends JpaRepository<Applicant , Long> {
}
