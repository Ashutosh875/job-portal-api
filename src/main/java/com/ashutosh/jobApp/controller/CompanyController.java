package com.ashutosh.jobApp.controller;

import com.ashutosh.jobApp.dto.request.CompanyRequestDto;
import com.ashutosh.jobApp.dto.response.CompanyResponseDto;
import com.ashutosh.jobApp.service.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PutMapping("/profile")
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<CompanyResponseDto> updateProfile(@Valid @RequestBody
                                                 CompanyRequestDto request){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(companyService.updateCompanyProfile(request));
    }

    @GetMapping
    public ResponseEntity<Page<CompanyResponseDto>> getAllCompanies(@RequestParam(defaultValue = "0") int page,
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
    public ResponseEntity<CompanyResponseDto> getCompanyById(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(companyService.getCompanyById(id));
    }

    @DeleteMapping("/profile")
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<Void> deleteCompany(){
        companyService.deleteCompany();
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
