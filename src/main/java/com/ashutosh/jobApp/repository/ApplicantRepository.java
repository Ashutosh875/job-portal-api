package com.ashutosh.jobApp.repository;

import com.ashutosh.jobApp.entity.Applicant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ApplicantRepository extends JpaRepository<Applicant , Long> {

    @Query("SELECT a FROM Applicant a JOIN a.jobs j WHERE j.id = :jobId ")
    Page<Applicant> findApplicantsByJobId(@Param("jobId") Long jobId, Pageable pageable);

    Optional<Applicant> findByUserId(Long id);

}
