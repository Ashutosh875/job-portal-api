package com.ashutosh.jobApp.controller;

import com.ashutosh.jobApp.entity.Job;
import com.ashutosh.jobApp.service.ApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

//    @GetMapping("/applicants/{applicantId}/jobs")
//    public ResponseEntity<List<Job>> applicantAllJobs(@PathVariable Long applicantId){
//
//    }




}
