package com.ashutosh.jobApp.repository;

import com.ashutosh.jobApp.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("SELECT c FROM Company c LEFT JOIN FETCH c.reviews")
    List<Company> findAllWithReviews();

}
