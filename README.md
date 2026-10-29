# Task Management API

A RESTful backend application built using Java and Spring Boot to create, retrieve, update, delete, and search tasks. The project demonstrates layered backend architecture, database integration, request validation, and centralized exception handling.

## Features

- Create a task
- Retrieve all tasks
- Retrieve a task by ID
- Update an existing task
- Delete a task
- Filter tasks by status and priority
- Search tasks by title or description using a keyword
- Validate incoming request data
- Handle resource-not-found and validation errors centrally
- Persist task data using Spring Data JPA and PostgreSQL

## Tech Stack

- **Language:** Java
- **Framework:** Spring Boot
- **Data Access:** Spring Data JPA / Hibernate
- **Database:** PostgreSQL
- **Build Tool:** Maven
- **Validation:** Jakarta Bean Validation
- **Version Control:** Git and GitHub

## Task Fields

A task includes the following fields:

| Field | Description |
|---|---|
| `id` | Unique task identifier |
| `title` | Task title |
| `description` | Task details |
| `status` | Task status, such as TODO, IN_PROGRESS, or DONE |
| `priority` | LOW, MEDIUM, or HIGH |
| `dueDate` | Task deadline |
| `createdAt` | Task creation timestamp |
| `updatedAt` | Last update timestamp |

## API Endpoints

Base path: `/api/tasks`

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/tasks` | Create a task |
| GET | `/api/tasks` | Get tasks, with optional filters |
| GET | `/api/tasks/{id}` | Get a task by ID |
| PUT | `/api/tasks/{id}` | Update a task |
| DELETE | `/api/tasks/{id}` | Delete a task |

### Filtering and Search

The task listing endpoint supports optional query parameters:

- `status` — filter by task status
- `priority` — filter by task priority
- `keyword` — search task titles and descriptions

Example:

`GET /api/tasks?status=TODO&priority=HIGH&keyword=backend`

You can use any supported combination of these parameters.

## Example: Create a Task

**Request**

`POST /api/tasks`

```json
{
  "title": "Learn Spring Boot",
  "description": "Practice REST API development",
  "status": "TODO",
  "priority": "HIGH",
  "dueDate": "2026-11-15"
}
```

**Expected result:** HTTP `201 Created`, with the created task returned in the response body.

## Error Handling

The application includes centralized exception handling for:

- Missing tasks — HTTP `404 Not Found`
- Request validation failures — HTTP `400 Bad Request`

## Project Architecture

The application follows a layered structure:

- **Controller:** Handles HTTP requests and responses.
- **Service:** Contains business logic and coordinates operations.
- **Repository:** Performs database access through Spring Data JPA.
- **Entity:** Represents the persisted task data.
- **DTO:** Defines request and response data structures.
- **Exception:** Contains custom exceptions and centralized error handling.

## Prerequisites

Install the required tools:

- Java version compatible with the project configuration
- PostgreSQL
- Maven, or use the Maven Wrapper if available

## Setup and Run

1. Clone the repository:

   ```bash
   git clone https://github.com/ravikumarpal2/task-management-api.git
   ```
   
2. Open the project directory:

   ```bash
   cd task-management-api
   ```


3.Create a PostgreSQL database named `task_management`.

4.Set the database environment variables before running the application.

   On Windows PowerShell:

   ```powershell
   $env:DB_URL="jdbc:postgresql://localhost:5432/task_management"
   $env:DB_USERNAME="postgres"
   $env:DB_PASSWORD="your_local_database_password"
5. Test the endpoints using Postman or another HTTP client.


## Future Improvements

- Pagination and sorting
- Automated unit and integration tests
- Authentication and authorization
- API documentation with OpenAPI/Swagger
- Production-ready deployment configuration
