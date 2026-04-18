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
| GET | `/api/jobs` | Get all jobs |
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
 
