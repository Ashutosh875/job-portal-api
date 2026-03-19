package com.ashutosh.jobApp.controller;

import com.ashutosh.jobApp.entity.Company;
import com.ashutosh.jobApp.service.CompanyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
    public ResponseEntity<Page<Company>> getAllCompanies(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "5") int size,
                                                         @RequestParam(defaultValue = "id") String sortBy,
                                                         @RequestParam(defaultValue = "asc") String sortDir){

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page , size , sort);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(companyService.getAllCompanies(pageable));
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
