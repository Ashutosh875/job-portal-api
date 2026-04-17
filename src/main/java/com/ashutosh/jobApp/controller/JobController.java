package com.ashutosh.jobApp.controller;


import com.ashutosh.jobApp.entity.Job;
import com.ashutosh.jobApp.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @PostMapping("/companies/jobs")
    @PreAuthorize("hasAnyRole('COMPANY' , 'ADMIN')")
    public ResponseEntity<Job> createJob(@RequestBody Job job){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(jobService.postJob(job));
    }

    @GetMapping("/jobs")
    public ResponseEntity<Page<Job>> findAllJobs(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Long minSalary,
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir){

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page , size , sort);

        if(location != null && minSalary == null && title == null){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(jobService.findJobByLocation(location,pageable));
        }

        if(location == null && minSalary != null && title == null){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(jobService.findJobByMinSalary(minSalary,pageable));
        }

        if(location == null && minSalary == null && title != null){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(jobService.findJobByTitle(title,pageable));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(jobService.findAllJobs(pageable));
    }

    @GetMapping("/jobs/{jobId}")
    public ResponseEntity<Job> findJobById(@PathVariable Long jobId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(jobService.findJobById(jobId));
    }


    @GetMapping("/companies/{companyId}/jobs")
    public ResponseEntity<List<Job>> findAllJobsByCompany(@PathVariable Long companyId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(jobService.findAllJobsByCompany(companyId));
    }

    @PutMapping("/jobs/{jobId}")
    @PreAuthorize("hasAnyRole('COMPANY' , 'ADMIN')")
    public ResponseEntity<Job> updateJob(@RequestBody Job job,
                                         @PathVariable Long jobId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(jobService.updateJobById(job,jobId));
    }

    @DeleteMapping("/jobs/{jobId}")
    @PreAuthorize("hasAnyRole('COMPANY' , 'ADMIN')")
    public ResponseEntity<String> deleteJob(@PathVariable Long jobId){
        jobService.deleteJobById(jobId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }


}
