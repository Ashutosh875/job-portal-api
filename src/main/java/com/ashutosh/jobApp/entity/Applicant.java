package com.ashutosh.jobApp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@SQLRestriction("is_active = false")
public class Applicant extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer experience;
    private String jobTitle;

    @Column(nullable = false)
    private boolean isActive = true;

    @ManyToMany(mappedBy = "applicants",
            cascade = {CascadeType.PERSIST , CascadeType.MERGE})
    private List<Job> jobs = new ArrayList<>();

    public Applicant(){
    }


}
