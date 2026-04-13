# Job Portal REST API

A production-style backend system built with Java and Spring Boot, based on platforms like Glassdoor and Naukri. Features full CRUD operations, JPA relationship management, search and filter APIs, pagination, sorting, transaction management, and soft delete.

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

### Company

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/companies` | Create a company |
| GET | `/api/companies` | Get all companies (paginated) |
| GET | `/api/companies/{id}` | Get company by ID |
| PUT | `/api/companies/{id}` | Update company |
| DELETE | `/api/companies/{id}` | Soft delete company |

### Job

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/companies/{companyId}/jobs` | Create job under a company |
| GET | `/api/jobs` | Get all jobs (paginated + filtered) |
| GET | `/api/jobs/{jobId}` | Get job by ID |
| GET | `/api/companies/{companyId}/jobs` | Get all jobs by company |
| PUT | `/api/jobs/{jobId}` | Update job |
| DELETE | `/api/jobs/{jobId}` | Delete job |

### Review

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/companies/{companyId}/reviews` | Post a review for a company |
| GET | `/api/companies/{companyId}/reviews` | Get all reviews (paginated) |
| GET | `/api/reviews/{reviewId}` | Get review by ID |
| PUT | `/api/reviews/{reviewId}` | Update review |
| DELETE | `/api/reviews/{reviewId}` | Delete review |

### Applicant

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/applicants` | Create applicant |
| GET | `/api/applicants` | Get all applicants |
| GET | `/api/applicants/{id}` | Get applicant by ID |
| PUT | `/api/applicants/{id}` | Update applicant |
| DELETE | `/api/applicants/{id}` | Soft delete applicant |

### Application (ManyToMany)

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/applicants/{applicantId}/jobs/{jobId}` | Apply to a job |
| DELETE | `/api/applicants/{applicantId}/jobs/{jobId}` | Withdraw application |
| GET | `/api/applicants/{applicantId}/jobs` | Get jobs applied by applicant |
| GET | `/api/jobs/{jobId}/applicants` | Get applicants for a job |

### Authentication

| Method | Endpoint                   | Description        |
|--------|----------------------------|--------------------|
| POST   | `/auth/register/applicant` | register applicant |
| POST   | `/auth/company/company`    | register company   |
| POST   | `/auth/login`              | login              |
 
---

## Search, Filter & Pagination

The `GET /api/jobs` endpoint supports dynamic filtering, pagination and sorting:

```
``
GET /api/jobs?page=0&size=10&sortBy=minSalary&sortDir=desc
GET /api/jobs?location=Delhi
GET /api/jobs?minSalary=50000
GET /api/jobs?title=backend
GET /api/jobs?location=Delhi&page=0&size=5
``
```
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
├── controller/        # REST controllers
├── entity/            # JPA entities
├── repository/        # Spring Data JPA repositories
├── service/           # Service interfaces
│   └── impl/          # Service implementations
├── dto/               # dtos
├── enums/             # enums - roles
├── security/          # Security package
    └── filter/        # Security filters
    └── util/          # Security utils
    └── Security config 
└── exception/         # Custom exceptions
```
 
---

## TODO

- [ ] Refactor job search/filter to Specification pattern
- [ ] Add Request/Response DTOs
- [ ] Refactor GlobalExceptionHandler
- [ ] Add Bean Validation on request bodies
- [ ] Add Swagger/OpenAPI documentation
- [ ] Write unit and integration tests

## Author

**Ashutosh** — [github.com/Ashutosh875](https://github.com/Ashutosh875)

> Built as part of a structured Spring Boot backend development learning .
 
