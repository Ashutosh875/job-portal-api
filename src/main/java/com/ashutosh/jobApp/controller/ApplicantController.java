package com.ashutosh.jobApp.controller;

import com.ashutosh.jobApp.dto.request.ApplicantRequestDto;
import com.ashutosh.jobApp.dto.response.ApplicantResponseDto;
import com.ashutosh.jobApp.entity.Applicant;
import com.ashutosh.jobApp.service.ApplicantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/applicants")
@RequiredArgsConstructor
public class ApplicantController {

    private final ApplicantService applicantService;

    @PutMapping("/profile")
    @PreAuthorize("hasRole('APPLICANT')")
    public ResponseEntity<ApplicantResponseDto> updateProfile(@RequestBody ApplicantRequestDto applicantRequestDto){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(applicantService.updateApplicantProfile(applicantRequestDto));
    }


    @GetMapping("/{applicantId}")
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<ApplicantResponseDto> getApplicantById(@PathVariable Long applicantId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(applicantService.getApplicantById(applicantId));
    }

    @DeleteMapping("/profile")
    @PreAuthorize("hasRole('APPLICANT')")
    public ResponseEntity<Void> deleteApplicant(){

        applicantService.deleteApplicant();

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
