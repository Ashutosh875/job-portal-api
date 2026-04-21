package com.ashutosh.jobApp.specification;

import com.ashutosh.jobApp.entity.Job;
import org.springframework.data.jpa.domain.Specification;

public class JobSpecification {

    public static Specification<Job> hasLocation(String location){
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("location")) ,"%" +  location + "%");
    }

    public static Specification<Job> hasMinSalary(Long minSalary){
        return (root , query , cb) ->
                cb.greaterThanOrEqualTo(root.get("minSalary") , minSalary);
    }

    public static Specification<Job> hasMaxSalary(Long maxSalary){
        return (root,query,cb) ->
                cb.lessThanOrEqualTo(root.get("maxSalary") , maxSalary);
    }

    public static Specification<Job> hasTitle(String title){
        return (root , query , cb) ->
                cb.like(cb.lower(root.get("title")) , "%" + title.toLowerCase() + "%");
    }
}
