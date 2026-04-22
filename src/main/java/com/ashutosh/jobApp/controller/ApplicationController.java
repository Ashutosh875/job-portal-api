package com.ashutosh.jobApp.controller;

import com.ashutosh.jobApp.dto.response.ApplicantResponseDto;
import com.ashutosh.jobApp.dto.response.JobResponseDto;
import com.ashutosh.jobApp.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;


    @PostMapping("/jobs/{jobId}/apply")
    @PreAuthorize("hasRole('APPLICANT')")
    public ResponseEntity<JobResponseDto> applyToJob(@PathVariable Long jobId){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(applicationService.applyToJob(jobId));
    }

    @DeleteMapping("/jobs/{jobId}/withdraw")
    @PreAuthorize("hasRole('APPLICANT')")
    public ResponseEntity<Void> withdrawApplication(@PathVariable Long jobId){

        applicationService.withdrawApplication(jobId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/applicants/my-applications")
    @PreAuthorize("hasRole('APPLICANT')")
    public ResponseEntity<Page<JobResponseDto>> getApplicationsOfApplicant(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "5") int size,
                                                         @RequestParam(defaultValue = "id") String sortBy,
                                                         @RequestParam(defaultValue = "asc") String sortDir){

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page , size , sort);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(applicationService.getApplicationsOfApplicant(pageable));

    }

    @GetMapping("/jobs/{jobId}/applicants")
    @PreAuthorize("hasAnyRole('COMPANY' , 'ADMIN')")
    public ResponseEntity<Page<ApplicantResponseDto>> getApplicantForJob(@PathVariable Long jobId,
                                                                         @RequestParam(defaultValue = "0") int page,
                                                                         @RequestParam(defaultValue = "5") int size,
                                                                         @RequestParam(defaultValue = "id") String sortBy,
                                                                         @RequestParam(defaultValue = "asc") String sortDir){
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page , size , sort);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(applicationService.getApplicantForJob(jobId , pageable));
    }
}
