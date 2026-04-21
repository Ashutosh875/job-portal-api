package com.ashutosh.jobApp.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true , callSuper = false)
@NoArgsConstructor
@SQLRestriction("is_active = true")
public class Company extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private String name;
    private String description;

    @Column(nullable = false)
    private boolean isActive = true;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    // one-to-many relation with job
    @OneToMany(mappedBy = "company",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Job> jobs = new ArrayList<>();

    public void addJob(Job job){
        jobs.add(job);
        job.setCompany(this);
    }

    // one-to-many relation with review
    @OneToMany(mappedBy = "company" ,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    public void addReview(Review review){
        reviews.add(review);
        review.setCompany(this);
    }

}
