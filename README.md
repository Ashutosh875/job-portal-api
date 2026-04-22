# Job Portal REST API

A production-style REST API built with Java 21 and Spring Boot, inspired by platforms
like Glassdoor and Naukri. Features JWT-based authentication, role-based access control,
ownership-based authorization, dynamic job search with JPA Specification, pagination,
sorting, soft delete, global exception handling, and full CRUD across jobs, companies,
applicants, and reviews.

## Tech Stack

| Technology      | Purpose                           |
|-----------------|-----------------------------------|
| Java 21         | Core language                     |
| Spring Boot 4.x | Application framework             |
| Spring Data JPA | Database abstraction layer        |
| Spring Security | Security layer                    |
| JJWT 0.13.x     | JWT token generation & validation |
| Hibernate       | JPA implementation / ORM          |
| PostgreSQL       | Relational database               |
| Maven           | Build tool                        |
| Postman         | API testing                       |
| Git & GitHub    | Version control                   |

## Entity Diagram

```
User
  ├── id, email, password, role
  │
  ├── @OneToOne ──► Applicant
  │                   ├── id, name, experience, jobTitle, isActive
  │                   ├── @OneToMany ──► Review
  │                   │                   └── id, title, description, rating
  │                   └── @ManyToMany ──► Job
  │                                        ├── id, title, description, minSalary, maxSalary, location
  │                                        └── @ManyToOne ──► Company
  │
  └── @OneToOne ──► Company
                      ├── id, name, description, isActive
                      ├── @OneToMany ──► Job
                      │                   └── id, title, description, minSalary, maxSalary, location
                      └── @OneToMany ──► Review
                                          └── id, title, description, rating
```
 
---

## API Endpoints

### Authentication

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register/applicant` | Register as applicant |
| POST | `/api/auth/register/company` | Register as company |
| POST | `/api/auth/login` | Login |

### Applicants

| Method | Endpoint | Description |
|--------|----------|-------------|
| PUT | `/api/applicants/profile` | Update applicant profile |
| GET | `/api/applicants/{applicantId}` | Get applicant by ID |
| DELETE | `/api/applicants/profile` | Delete applicant |

### Companies

| Method | Endpoint | Description |
|--------|----------|-------------|
| PUT | `/api/companies/profile` | Update company profile | 
| GET | `/api/companies` | Get all companies|
| GET | `/api/companies/{id}` | Get company by ID |
| DELETE | `/api/companies/profile` | Delete company | 

### Jobs

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/companies/jobs` | Create job | 
| GET | `/api/jobs` | Search jobs (filter by location, title, minSalary, maxSalary) |
| GET | `/api/jobs/{jobId}` | Get job by ID |
| GET | `/api/companies/{companyId}/jobs` | Get all jobs by company |
| PUT | `/api/jobs/{jobId}` | Update job |
| DELETE | `/api/jobs/{jobId}` | Delete job |

### Applications

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/jobs/{jobId}/apply` | Apply to job |
| DELETE | `/api/jobs/{jobId}/withdraw` | Withdraw application |
| GET | `/api/applicants/my-applications` | Get my applications |
| GET | `/api/jobs/{jobId}/applicants` | Get applicants for a job |

### Reviews

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/companies/{companyId}/reviews` | Get all reviews (paginated) |
| POST | `/api/companies/{companyId}/reviews` | Post a review |
| GET | `/api/reviews/{reviewId}` | Get review by ID |
| PUT | `/api/reviews/{reviewId}` | Update review |
| DELETE | `/api/reviews/{reviewId}` | Delete review | 

## Setup & Run Locally

### Prerequisites
- Java 21+
- PostgreSQL
- Maven

### Steps

**1. Clone the repository**
```bash
git clone https://github.com/Ashutosh875/job-portal-api.git
cd job-portal-api
```

**2. Create PostgreSQL database**
```sql
CREATE DATABASE jobapp;
```

**3. Configure `application.properties`**
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/jobapp
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

jwt.secret=your_secret_key
jwt.expiration=your_custom_expiration
```

**4. Run the application**
```bash
mvn spring-boot:run
```

**5. Access the API**
```
http://localhost:8080/api
```
 
---

## Project Structure

```
src/main/java/com/ashutosh/jobApp/
├── config/            # SwaggerConfig, SecurityConfig
├── controller/        # REST controllers
├── dto/
│   ├── request/       # Request DTOs
│   └── response/      # Response DTOs
├── entity/            # JPA entities
├── enums/             # Role enums
├── exception/         # Custom exceptions + GlobalExceptionHandler
├── mapper/            # Manual mappers - Applicant, Company, Job, Review
├── repository/        # Spring Data JPA repositories
├── security/
│   ├── filter/        # JwtAuthenticationFilter
│   └── util/          # JwtUtil, SecurityUtils
├── service/
│   └── impl/          # Service implementations
└── specification/     # JobSpecification
```
 
---

## TODO

- [ ] Write unit and integration tests

## Author

**Ashutosh** — [github.com/Ashutosh875](https://github.com/Ashutosh875)

 
