# TodoList_Springboot

This is a RESTful Todo List application built with Spring Boot, PostgreSQL, Flyway, MapStruct, Swagger, and JWT Authentication.

## Description

This project provides a robust REST API for managing a todo list, incorporating modern development practices and technologies. Key features include:

* **RESTful API:** Well-defined endpoints for CRUD operations on todo items.
* **PostgreSQL Database:** Persistent data storage using a relational database.
* **Flyway Database Migrations:** Automated database schema management.
* **MapStruct:** Type-safe bean mapping for efficient data transfer.
* **Swagger/OpenAPI:** Interactive API documentation for easy testing.
* **JWT Authentication:** Secure user authentication using JSON Web Tokens.

## Technologies Used

* Spring Boot
* Java
* PostgreSQL
* Flyway
* MapStruct
* Swagger/OpenAPI 3
* Spring Security with JWT
* Maven

## Getting Started

### Prerequisites

* Java Development Kit (JDK) 17 or higher
* Maven
* PostgreSQL database instance
* A tool like Postman or curl for API testing

### Installation

1.  Clone the repository:

    ```bash
    git clone [https://github.com/EricRaw512/TodoList_Springboot.git](https://github.com/EricRaw512/TodoList_Springboot.git)
    ```

2.  Navigate to the project directory:

    ```bash
    cd TodoList_Springboot
    ```

3.  Configure PostgreSQL:
  * Create a database.
  * Update the `application.properties` file with your database credentials.

4.  Build the project using Maven:

    ```bash
    mvn clean install
    ```

### Running the Application

1.  Run the Spring Boot application:

    ```bash
    mvn spring-boot:run
    ```

2.  The application will be accessible at `http://localhost:8080`.

3.  Access Swagger UI for API documentation: `http://localhost:8080/swagger-ui/index.html`

### Authentication

* This API is protected with JWT authentication.
* You will need to register and login to obtain a JWT token.
* Include the `Authorization: Bearer <token>` header in your requests.

### API Endpoints

* `/api/register`: Register a new user.
* `/api/login`: Login and obtain a JWT token.
* `/api/checklist`: Get all checklist (requires authentication).
* `/api/checklist`: Create checklist (requires authentication).
* `/api/checklist/{checklistId}/item/{checklistItemId}`: Get checklist item detail (requires authentication).
* `/api/checklist/{checklistId}/item/{checklistItemId}`: Update checklist item status (requires authentication).
* `/api/checklist/{checklistId}/item/{checklistItemId}`: Delete a checklist item (requires authentication).
* `/api/checklist/{checklistId}/item/rename/{checklistItemId}`: Rename checklist item (requires authentication).
* `/api/checklist/{checklistId}/item` : Get all checklist item (requires authentication).
* `/api/checklist/{checklistId}/item` : Create checklist item (requires authentication).

### Example Usage (using curl)

* **Register a user:**

    ```bash
    curl -X POST -H "Content-Type: application/json" -d '{"username": "testuser", "password": "password"}' http://localhost:8080/auth/register
    ```

* **Login and get token:**

    ```bash
    curl -X POST -H "Content-Type: application/json" -d '{"username": "testuser", "password": "password"}' http://localhost:8080/auth/login
    ```

* **Get all todos (with token):**

    ```bash
    curl -H "Authorization: Bearer <your_token>" http://localhost:8080/todos
    ```
