package com.ashutosh.jobApp.repository;

import com.ashutosh.jobApp.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface JobRepository extends JpaRepository<Job, Long> , JpaSpecificationExecutor<Job> {

    Page<Job> findAllByCompanyId(Long companyId , Pageable pageable);

    @Query("SELECT j FROM Job j JOIN j.applicants a WHERE a.id = :applicantId")
    Page<Job> findJobsByApplicantId(@Param("applicantId") Long applicantId, Pageable pageable);

}
