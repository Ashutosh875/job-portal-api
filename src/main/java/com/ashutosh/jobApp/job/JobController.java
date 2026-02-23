package com.ashutosh.jobApp.job;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public ResponseEntity<List<Job>> findAll(){
        return new ResponseEntity<>(jobService.findAll() , HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Job> createJob(@RequestBody Job job){
        return new ResponseEntity<>(jobService.createJob(job) , HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id){
        jobService.deleteJobById(id);
        return new ResponseEntity<>("Job with id : " + id + "deleted successfully" , HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Job> updateJob(@RequestBody Job job , @PathVariable Long id){
        return new ResponseEntity<>(jobService.updateJobById(job , id), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> findJobById(@PathVariable Long id){
        return new ResponseEntity<>(jobService.findJobById(id) , HttpStatus.OK);
    }
}
