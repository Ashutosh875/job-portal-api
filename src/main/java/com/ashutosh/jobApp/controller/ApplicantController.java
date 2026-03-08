package com.ashutosh.jobApp.controller;

import com.ashutosh.jobApp.entity.Applicant;
import com.ashutosh.jobApp.service.ApplicantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/applicants")
public class ApplicantController {

    private final ApplicantService applicantService;

    public ApplicantController(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }


    @PostMapping
    public ResponseEntity<Applicant> createApplicant(@RequestBody Applicant applicant){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(applicantService.createApplicant(applicant));
    }

    @GetMapping("/{applicantId}")
    public ResponseEntity<Applicant> getApplicantById(@PathVariable Long applicantId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(applicantService.getApplicantById(applicantId));
    }

    @PutMapping("/{applicantId}")
    public ResponseEntity<Applicant> updateApplicant(@PathVariable Long applicantId,
                                                     @RequestBody Applicant applicant){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(applicantService.updateApplicant(applicant , applicantId));
    }

    @DeleteMapping("/{applicantId}")
    public ResponseEntity<Void> deleteApplicant(@PathVariable Long applicantId){

        applicantService.deleteApplicant(applicantId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
