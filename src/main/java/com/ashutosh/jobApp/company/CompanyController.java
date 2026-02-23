package com.ashutosh.jobApp.company;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies(){
        return new ResponseEntity<>(companyService.getAllCompanies() , HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Company> createCompany(@RequestBody Company company){
        return ResponseEntity.status(HttpStatus.CREATED).body(companyService.createCompany(company));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable Long id){
        companyService.deleteCompany(id);
        return ResponseEntity.status(HttpStatus.OK).body("Company with : " + id + " Deleted successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Company> updateCompany(@RequestBody Company company , @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(companyService.updateCompany(company , id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(companyService.getCompanyById(id));
    }
}
