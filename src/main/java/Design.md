# Library Management System API Documentation

## Project Overview

The Library Management System API allows librarians to manage books, patrons, and borrowing records. It provides RESTful endpoints for CRUD operations on books and patrons, as well as endpoints for borrowing and returning books. This project is built using Spring Boot and supports optional features like security, caching, and logging with aspects.

## Table of Contents

1. [Entities](#entities)
2. [API Endpoints](#api-endpoints)
    - [Book Management](#book-management)
    - [Patron Management](#patron-management)
    - [Borrowing Management](#borrowing-management)
3. [Data Storage](#data-storage)
4. [Validation and Error Handling](#validation-and-error-handling)
5. [Security (Optional)](#security-optional)
6. [Aspects (Optional)](#aspects-optional)
7. [Caching (Optional)](#caching-optional)
8. [Transaction Management](#transaction-management)
9. [Testing](#testing)
10. [Running the Application](#running-the-application)
11. [API Usage Guide](#api-usage-guide)

---

## Entities

Define the following entities with the specified attributes:

- **Book**
    - `id`: Unique identifier for the book (primary key).
    - `title`: Title of the book.
    - `author`: Author of the book.
    - `publicationYear`: Year of publication.
    - `isbn`: International Standard Book Number.
    - `numberOfCopies`: Number of Copies.
    

- **Patron**
    - `id`: Unique identifier for the patron (primary key).
    - `name`: Full name of the patron.
    - `contactInfo`: Contact information such as email or phone number.


- **Borrowing Record**
    - `id`: Unique identifier for the record.
    - `book`: Reference to the borrowed book.
    - `patron`: Reference to the patron who borrowed the book.
    - `borrowDate`: Date when the book was borrowed.
    - `returnDate`: Date when the book was returned (optional).

---

## API Endpoints

### Book Management

- **GET /api/books**  
  Retrieves a list of all books in the library.

- **GET /api/books/{id}**  
  Retrieves details of a specific book by `id`.

- **POST /api/books**  
  Adds a new book to the library. Requires `title`, `author`, `publicationYear`,`isbn`, and `numberOfCopies` in the request body.

- **PUT /api/books/{id}**  
  Updates information for an existing book by `id`.

- **DELETE /api/books/{id}**  
  Removes a book from the library by `id`.

### Patron Management

- **GET /api/patrons**  
  Retrieves a list of all patrons.

- **GET /api/patrons/{id}**  
  Retrieves details of a specific patron by `id`.

- **POST /api/patrons**  
  Adds a new patron to the system. Requires `name` and `contactInfo` in the request body.

- **PUT /api/patrons/{id}**  
  Updates information for an existing patron by `id`.

- **DELETE /api/patrons/{id}**  
  Removes a patron from the system by `id`.

### Borrowing Management

- **POST /api/borrow/{bookId}/patron/{patronId}**  
  Allows a patron to borrow a book. Requires `bookId` and `patronId`.

- **PUT /api/return/{bookId}/patron/{patronId}**  
  Records the return of a borrowed book by `bookId` and `patronId`.

---

## Data Storage

- Use **H2** (default), **MySQL**, or **PostgreSQL** databases for storing entity details.
- Set up relationships:
    - **One-to-Many** between `Book` and `BorrowingRecord` (a book may have multiple borrowing records).
    - **One-to-Many** between `Patron` and `BorrowingRecord` (a patron may have multiple borrowing records).

---

## Validation and Error Handling

- **Validation**: Ensure that required fields (e.g., `title` for Book, `name` for Patron) are provided and are in the correct format.
- **Error Handling**: Use exception handlers to catch and respond with appropriate HTTP status codes, such as:
    - `400 Bad Request` for invalid input.
    - `404 Not Found` if a requested resource does not exist.
    - `500 Internal Server Error` for unexpected issues.

---

## Security (Optional)

- **Authentication**: Use **Basic Authentication** or **JWT** for securing endpoints.
    - Secure endpoints based on roles, such as restricting book management to users with librarian roles.
- **Authorization**: Apply role-based access control to limit actions based on user permissions.

---

## Aspects (Optional)

Implement **Aspect-Oriented Programming (AOP)** with the following functionality:

- **Logging**: Automatically log method calls, parameters, and return values for key operations (e.g., book addition, patron transactions).
- **Performance Monitoring**: Log the time taken by methods such as adding or retrieving books and patrons to identify potential bottlenecks.
- **Exception Handling**: Log exceptions with relevant details to aid debugging.

---

## Caching (Optional)

Utilize Spring's caching mechanisms to optimize performance:

- **Cache Configuration**: Cache frequently accessed resources such as book details and patron information.
- **Expiry and Invalidation**: Set cache expiry or implement invalidation upon data updates to ensure cache accuracy.

---

## Transaction Management

Apply **@Transactional** annotations for data integrity:

- Use `@Transactional` on methods for borrowing and returning books to ensure atomicity.
- Use `@Transactional(readOnly = true)` for read-only methods to optimize performance.

---

## Testing

- **Unit Tests**: Use JUnit and Mockito to write tests for service and repository layers.
    - Verify CRUD operations for books, patrons, and borrowing records.
    - Test borrowing and returning functionalities, ensuring valid updates to `BorrowingRecord`.
- **Integration Tests**: Write tests using SpringBootTest to validate interactions between layers.
- **Coverage**: Ensure adequate coverage for all functionalities, including edge cases and validation errors.

---

## Running the Application

1. **Clone the Repository**:
   ```bash
   git clone <repository-url>
   

