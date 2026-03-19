package com.ashutosh.jobApp.repository;

import com.ashutosh.jobApp.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findAllByCompanyId(Long companyId);

    @Query("SELECT j FROM Job j WHERE j.location = :location")
    Page<Job> findJobsByLocation(@Param("location") String location , Pageable pageable);

    @Query("SELECT j FROM Job j WHERE j.minSalary >= :minSalary")
    Page<Job> findJobsByMinSalary(@Param("minSalary") Long minSalary , Pageable pageable);

    @Query("SELECT j FROM Job j WHERE LOWER(j.title) LIKE LOWER(CONCAT('%', :title , '%'))")
    Page<Job> findJobsByTitle(@Param("title") String title , Pageable pageable);
}
