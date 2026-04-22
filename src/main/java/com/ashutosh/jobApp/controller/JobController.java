package com.ashutosh.jobApp.controller;


import com.ashutosh.jobApp.dto.request.JobRequestDto;
import com.ashutosh.jobApp.dto.response.JobResponseDto;
import com.ashutosh.jobApp.service.JobService;
import jakarta.validation.Valid;
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
public class JobController {

    private final JobService jobService;

    @PostMapping("/companies/jobs")
    @PreAuthorize("hasAnyRole('COMPANY' , 'ADMIN')")
    public ResponseEntity<JobResponseDto> postJob(@Valid @RequestBody JobRequestDto jobRequestDto){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(jobService.postJob(jobRequestDto));
    }

    @GetMapping("/jobs")
    public ResponseEntity<Page<JobResponseDto>> searchJobs(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Long minSalary,
            @RequestParam(required = false) Long maxSalary,
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir){

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page , size , sort);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(jobService.searchJobs(location , minSalary ,maxSalary, title ,pageable));
    }

    @GetMapping("/jobs/{jobId}")
    public ResponseEntity<JobResponseDto> findJobById(@PathVariable Long jobId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(jobService.findJobById(jobId));
    }


    @GetMapping("/companies/{companyId}/jobs")
    public ResponseEntity<Page<JobResponseDto>> findAllJobsByCompany(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @PathVariable Long companyId){

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                :Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page , size , sort);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(jobService.findAllJobsByCompany(companyId , pageable));
    }

    @PutMapping("/jobs/{jobId}")
    @PreAuthorize("hasAnyRole('COMPANY' , 'ADMIN')")
    public ResponseEntity<JobResponseDto> updateJob(@Valid @RequestBody JobRequestDto jobRequestDto,
                                         @PathVariable Long jobId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(jobService.updateJobById(jobRequestDto,jobId));
    }

    @DeleteMapping("/jobs/{jobId}")
    @PreAuthorize("hasAnyRole('COMPANY' , 'ADMIN')")
    public ResponseEntity<Void> deleteJob(@PathVariable Long jobId){
        jobService.deleteJobById(jobId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }


}
