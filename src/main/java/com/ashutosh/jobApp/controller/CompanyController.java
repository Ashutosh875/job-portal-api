package com.ashutosh.jobApp.controller;

import com.ashutosh.jobApp.entity.Company;
import com.ashutosh.jobApp.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    public ResponseEntity<Company> createCompany(@RequestBody Company company){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(companyService.createCompany(company));
    }

    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(companyService.getAllCompanies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(companyService.getCompanyById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable Long id){
        companyService.deleteCompany(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Company> updateCompany(@RequestBody Company company , @PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(companyService.updateCompany(company , id));
    }

}
