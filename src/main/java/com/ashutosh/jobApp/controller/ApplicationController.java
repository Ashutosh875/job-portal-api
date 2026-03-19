package com.ashutosh.jobApp.controller;

import com.ashutosh.jobApp.entity.Applicant;
import com.ashutosh.jobApp.entity.Job;
import com.ashutosh.jobApp.service.ApplicationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping("/applicants/{applicantId}/jobs/{jobId}")
    public ResponseEntity<Job> applyToJob(@PathVariable Long applicantId,
                                          @PathVariable Long jobId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(applicationService.applyToJob(applicantId , jobId));
    }

    @DeleteMapping("/applicants/{applicantId}/jobs/{jobId}")
    public ResponseEntity<Void> withdrawApplication(@PathVariable Long applicantId,
                                                    @PathVariable Long jobId){

        applicationService.withdrawApplication(applicantId , jobId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/applicants/{applicantId}/jobs")
    public ResponseEntity<Page<Job>> getJobsForApplicant(@PathVariable Long applicantId,
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
                .body(applicationService.getJobsForApplicant(applicantId , pageable));

    }

    @GetMapping("/jobs/{jobId}/applicants")
    public ResponseEntity<Page<Applicant>> getApplicantForJob(@PathVariable Long jobId,
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
