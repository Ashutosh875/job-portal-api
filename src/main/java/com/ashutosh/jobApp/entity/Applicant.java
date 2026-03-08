package com.ashutosh.jobApp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Applicant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer experience;
    private String jobTitle;

    @ManyToMany(mappedBy = "applicants",
            cascade = {CascadeType.PERSIST , CascadeType.MERGE})
    private List<Job> jobs = new ArrayList<>();

    public Applicant(){
    }


}
