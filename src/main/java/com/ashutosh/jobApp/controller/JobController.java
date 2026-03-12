package com.ashutosh.jobApp.controller;


import com.ashutosh.jobApp.entity.Job;
import com.ashutosh.jobApp.service.JobService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }


    // create job with company id
    @PostMapping("/companies/{companyId}/jobs")
    public ResponseEntity<Job> createJob(@RequestBody Job job,
                                         @PathVariable Long companyId){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(jobService.createJob(job,companyId));
    }

    // get all jobs
    @GetMapping("/jobs")
    public ResponseEntity<Page<Job>> findAllJobs(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Long minSalary,
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){

        Pageable pageable = PageRequest.of(page , size);

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

    // get job by job id
    @GetMapping("/jobs/{jobId}")
    public ResponseEntity<Job> findJobById(@PathVariable Long jobId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(jobService.findJobById(jobId));
    }

    // get all jobs by company id
    @GetMapping("/companies/{companyId}/jobs")
    public ResponseEntity<List<Job>> findAllJobsByCompany(@PathVariable Long companyId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(jobService.findAllJobsByCompany(companyId));
    }

    @PutMapping("/jobs/{jobId}")
    public ResponseEntity<Job> updateJob(@RequestBody Job job,
                                         @PathVariable Long jobId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(jobService.updateJobById(job,jobId));
    }

    @DeleteMapping("/jobs/{jobId}")
    public ResponseEntity<String> deleteJob(@PathVariable Long jobId){
        jobService.deleteJobById(jobId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }


}
