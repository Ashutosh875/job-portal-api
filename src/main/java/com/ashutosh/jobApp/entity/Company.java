package com.ashutosh.jobApp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    public Company() {
    }


    // one-to-many relation with job
    @OneToMany(mappedBy = "company",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonIgnore
    private List<Job> jobs;

    public void addJob(Job job){
        jobs.add(job);
        job.setCompany(this);
    }

    // one-to-many relation with review
    @OneToMany(mappedBy = "company" ,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonIgnore
    private List<Review> reviews;

    public void addReview(Review review){
        reviews.add(review);
        review.setCompany(this);
    }

}
