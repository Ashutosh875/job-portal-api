package com.ashutosh.jobApp.repository;

import com.ashutosh.jobApp.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("SELECT c FROM Company c LEFT JOIN FETCH c.reviews")
    Page<Company> findAllWithReviews(Pageable pageable);

    Optional<Company> findByUserId(Long id);

}
