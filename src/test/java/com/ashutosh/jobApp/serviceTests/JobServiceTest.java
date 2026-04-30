package com.ashutosh.jobApp.serviceTests;

import com.ashutosh.jobApp.dto.request.JobRequestDto;
import com.ashutosh.jobApp.dto.response.JobResponseDto;
import com.ashutosh.jobApp.entity.Company;
import com.ashutosh.jobApp.entity.Job;
import com.ashutosh.jobApp.entity.User;
import com.ashutosh.jobApp.exception.ResourceNotFoundException;
import com.ashutosh.jobApp.mapper.JobMapper;
import com.ashutosh.jobApp.repository.JobRepository;
import com.ashutosh.jobApp.service.CompanyService;
import com.ashutosh.jobApp.service.impl.JobServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class JobServiceTest {

    @InjectMocks
    private JobServiceImpl jobService;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private CompanyService companyService;

    @Mock
    private JobMapper jobMapper;

    @Test
    void findJobById_shouldReturnJobResponseDto_WhenJobExists(){

        long jobId = 1L;

        Job mockJob = new Job();
        mockJob.setId(jobId);

        JobResponseDto mockResponse = new JobResponseDto();

        Mockito.when(jobRepository.findById(jobId)).thenReturn(Optional.of(mockJob));
        Mockito.when(jobMapper.toResponse(mockJob)).thenReturn(mockResponse);

        JobResponseDto result = jobService.findJobById(jobId);

        assertNotNull(result);
        verify(jobRepository , times(1)).findById(jobId);
        verify(jobMapper , times(1)).toResponse(mockJob);
    }

    @Test
    void findJobById_ShouldThrowResourceNotFoundException_WhenJobNotFound(){

        Long jobId = 99L;

        when(jobRepository.findById(jobId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class , () -> jobService.findJobById(jobId));
        verify(jobRepository , times(1)).findById(jobId);
    }

    @Test
    void postJob_ShouldReturnJobResponseDto_WhenCompanyIsAuthenticated(){

        JobRequestDto mockJobReqDto = new JobRequestDto();
        User mockUser = new User();
        mockUser.setEmail("company@test.com");
        Company mockCompany = new Company();
        mockCompany.setUser(mockUser);
        Job mockJob = new Job();
        JobResponseDto mockResponse = new JobResponseDto();

        when(companyService.getAuthenticatedCompany()).thenReturn((mockCompany));
        when(jobMapper.toEntity(mockJobReqDto)).thenReturn(mockJob);
        when(jobRepository.save(mockJob)).thenReturn(mockJob);
        when(jobMapper.toResponse(mockJob)).thenReturn(mockResponse);

        JobResponseDto result = jobService.postJob(mockJobReqDto);

        assertNotNull(result);
        assertEquals(mockCompany , mockJob.getCompany());
        verify(jobRepository , times(1)).save(mockJob);
        verify(jobMapper , times(1)).toEntity(mockJobReqDto);
        verify(jobMapper , times(1)).toResponse(mockJob);
        verify(companyService ,times(1)).getAuthenticatedCompany();

    }

    @Test
    void updateJob_ShouldReturnUpdatedJobResponse_WhenCompanyIsAuthorized(){

        User mockUser = new User();
        mockUser.setEmail("company@test.com");

        Company mockCompany = new Company();
        mockCompany.setId(1L);
        mockCompany.setUser(mockUser);

        Job mockJob = new Job();
        Long jobId = 1L;
        mockJob.setId(jobId);
        mockJob.setCompany(mockCompany);

        Job updatedFields = new Job();
        JobRequestDto mockRequest = new JobRequestDto();
        JobResponseDto mockResponse = new JobResponseDto();

        when(companyService.getAuthenticatedCompany()).thenReturn(mockCompany);
        when(jobMapper.toEntity(mockRequest)).thenReturn(updatedFields);
        when(jobRepository.findById(jobId)).thenReturn(Optional.of(mockJob));
        when(jobRepository.save(mockJob)).thenReturn(mockJob);
        when(jobMapper.toResponse(mockJob)).thenReturn(mockResponse);

        JobResponseDto result = jobService.updateJobById(mockRequest , jobId);

        assertNotNull(result);
        assertEquals(mockResponse , result);
        assertEquals(mockCompany , mockJob.getCompany());
        verify(jobRepository , times(1)).save(mockJob);
        verify(jobMapper , times(1)).toEntity(mockRequest);
        verify(jobRepository , times(1)).findById(jobId);
        verify(jobMapper , times(1)).toResponse(mockJob);
        verify(companyService ,times(1)).getAuthenticatedCompany();
    }

    @Test
    void updateJob_ShouldThrowAccessDeniedExc_WhenCompanyIsUnauthorized(){

        User mockUser = new User();
        mockUser.setEmail("company@test.com");

        JobRequestDto requestDto = new JobRequestDto();

        Company mockCompany = new Company();
        mockCompany.setId(1L);
        mockCompany.setUser(mockUser);

        Company anotherCompany = new Company();
        anotherCompany.setId(2L);

        Job mockJob =  new Job();
        Long jobId = 99L;
        mockJob.setId(jobId);
        mockJob.setCompany(anotherCompany);

        when(jobRepository.findById(jobId)).thenReturn(Optional.of(mockJob));
        when(companyService.getAuthenticatedCompany()).thenReturn(mockCompany);

        assertThrows(AccessDeniedException.class , () -> jobService.updateJobById(requestDto , jobId));
        verify(jobRepository , times(1)).findById(jobId);
        verify(companyService ,times(1)).getAuthenticatedCompany();
    }

}
