# RentRead - Online Book Rental System

## Project Overview
This project implements a RESTful API service using Spring Boot for an online book rental system. The application allows users to register, log in, and rent books. Administrators can manage book details such as adding, updating, and deleting books. The system uses **H2 in-memory database** and supports authentication and authorization using **Basic Authentication**.

## Key Features

1. **User Registration & Login**
    - Users can register with email, password, first name, and last name.
    - Passwords are encrypted using BCrypt.
    - Users can log in using their email and password.

2. **Book Management**
    - Users can browse all available books.
    - Administrators can create, update, and delete books.
    - Fields include: Title, Author, Genre, and Availability Status.

3. **Rental Management**
    - Users can rent books, but cannot have more than two active rentals.
    - Users can return rented books.

4. **Authentication & Authorization**
    - **Basic Authentication** is used.
    - Two roles: `USER` and `ADMIN`.
    - Public endpoints are accessible to everyone (e.g., registration and login).
    - Private endpoints are accessible only to authenticated users. Admin-specific actions are restricted to `ADMIN` role.

5. **Unit Testing**
    - The project includes unit tests using **MockMvc** and **Mockito** for key functionalities.

## Technologies Used

- **Spring Boot** (REST API)
- **Spring Security** (Authentication & Authorization)
- **H2 Database** (In-memory database for testing)
- **Maven** (Dependency Management)
- **JUnit** & **Mockito** (Unit Testing)
- **Postman** (API Testing - Collection included in `src/main/resources`)

## Getting Started

### Prerequisites

- JDK 11 or later
- Maven 3.6+
- Postman (optional, for testing)

### Running the Application

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/naveenerroju/rentread.git

2. **Navigate to the Project Directory**:
   ```bash
   cd rentread

3. **Build the Application**
    ```bash
   mvn clean install

4. **Run the Application**
   ```bash
   mvn spring-boot:run

**Alternatively, you can build a JAR file and run it:**
```bash
   mvn clean package
   java -jar target/rentread-0.0.1-SNAPSHOT.jar
```
### Access the H2 Database Console

- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (leave blank)

### API Endpoints

| Method | Endpoint                  | Description                          | Access Level |
|--------|---------------------------|--------------------------------------|--------------|
| POST   | `/unauth/register/user`   | Register a new user                  | Public       |
| POST   | `/unauth/register/admin`  | Register a new admin                 | Public       |
| POST   | `/manage/book`            | Add a book into the books repository | Admin        |
| GET    | `/book/available`         | Get all available books              | User/Admin   |
| GET    | `/books`                  | Get all books                        | Admin Only   |
| PUT    | `/rent/borrow/{bookId}`   | Rent a book                          | User Only    |
| POST   | `/books/{bookId}/return`  | Return a rented book                 | User Only    |
| DELETE | `/rent/return/1`          | Delete a book                        | Admin Only   |

## Error Handling

The API handles common errors and returns appropriate HTTP status codes:
- **404** - Resource not found (e.g., book or user not found)
- **400** - Bad request (e.g., invalid input)
- **401** - Unauthorized (e.g., accessing private endpoints without login)
- **403** - Forbidden (e.g., insufficient permissions)

### Authentication

- The API uses **Basic Authentication**.
- After registering, you can log in by providing the username (email) and password in the request header using Postman or any API client.
- Once logged in, use the generated session to make authorized API calls without resending credentials for some time.

### Configuration

- The application uses **H2 in-memory database** for persistence. No additional setup is required.
- **Spring Security** is used for securing the API with Basic Authentication.

### Unit Testing

Unit tests are included and cover key functionalities like user registration, book management, and rental transactions.

To run the tests:
```bash
mvn test
```

### Summary of Contents:
- **Project Overview**: Brief summary of the project and its key features.
- **Technologies Used**: List of technologies (Spring Boot, H2, Maven, etc.).
- **Getting Started**: Instructions to clone, build, and run the project.
- **Postman Collection**: Instructions for using the Postman collection.
- **API Endpoints**: List of key API endpoints with their descriptions and access levels.
- **Authentication**: Information about Basic Authentication.
- **Unit Testing**: Instructions for running unit tests.
- **Error Handling**: HTTP codes returned for common errors.
- **Logging**: Explanation about logging configuration.
