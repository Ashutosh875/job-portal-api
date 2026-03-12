package com.ashutosh.jobApp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Long minSalary;
    private Long maxSalary;
    private String location;

    public Job() {
    }

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToMany
    @JoinTable(
            name = "job_applicant",
            joinColumns = @JoinColumn(name = "job_id"),
            inverseJoinColumns = @JoinColumn(name = "applicant_id")
    )
    @JsonIgnore
    private List<Applicant> applicants = new ArrayList<>();

    public void addApplicant(Applicant applicant){
        applicants.add(applicant);
        applicant.getJobs().add(this);
    }

    public void removeApplicant(Applicant applicant){
        this.applicants.remove(applicant);
        applicant.getJobs().remove(this);
    }

}
