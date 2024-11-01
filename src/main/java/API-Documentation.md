# Library Management System API Documentation

## Getting Started

### Prerequisites
- Java 23
- Maven 3.6+
- H2 Database (embedded)

### Running the Application
1. Clone the project repository.
2. Build the project using Maven:
3. Run the project:
4. The application will run on `http://localhost:8081/`.

5. Access the **H2 Console** at `http://localhost:8081/h2-console`:
- JDBC URL: `jdbc:h2:mem:testdb`
- User: `sa`
- Password: `password`

## API Endpoints

### Book Management

| HTTP Method | Endpoint                 | Description                           | Request Body            | Response Code | Example Request                                    |
|-------------|--------------------------|---------------------------------------|-------------------------|---------------|----------------------------------------------------|
| GET         | `/api/books`              | Retrieve all books                    | -                       | 200           | `curl -X GET http://localhost:8081/api/books`      |
| GET         | `/api/books/{id}`         | Retrieve a specific book by ID        | -                       | 200, 404      | `curl -X GET http://localhost:8081/api/books/1`    |
| POST        | `/api/books`              | Add a new book                        | `{ "title": "...", "author": "...", "isbn": "...", "publicationYear": 2020 }` | 201           | `curl -X POST http://localhost:8081/api/books`     |
| PUT         | `/api/books/{id}`         | Update an existing book               | `{ "title": "...", "author": "...", "isbn": "...", "publicationYear": 2020 }` | 200, 404      | `curl -X PUT http://localhost:8081/api/books/1`    |
| DELETE      | `/api/books/{id}`         | Remove a book from the library        | -                       | 200, 404      | `curl -X DELETE http://localhost:8081/api/books/1` |

### Patron Management

| HTTP Method | Endpoint                 | Description                           | Request Body            | Response Code | Example Request                                      |
|-------------|--------------------------|---------------------------------------|-------------------------|---------------|------------------------------------------------------|
| GET         | `/api/patrons`            | Retrieve all patrons                  | -                       | 200           | `curl -X GET http://localhost:8081/api/patrons`      |
| GET         | `/api/patrons/{id}`       | Retrieve a specific patron by ID      | -                       | 200, 404      | `curl -X GET http://localhost:8081/api/patrons/1`    |
| POST        | `/api/patrons`            | Add a new patron                      | `{ "name": "...", "contactInfo": "..." }` | 201           | `curl -X POST http://localhost:8081/api/patrons`     |
| PUT         | `/api/patrons/{id}`       | Update an existing patron             | `{ "name": "...", "contactInfo": "..." }` | 200, 404      | `curl -X PUT http://localhost:8081/api/patrons/1`    |
| DELETE      | `/api/patrons/{id}`       | Remove a patron from the system       | -                       | 200, 404      | `curl -X DELETE http://localhost:8081/api/patrons/1` |

### Borrowing Management

| HTTP Method | Endpoint                                  | Description                                    | Request Body | Response Code | Example Request                                            |
|-------------|-------------------------------------------|------------------------------------------------|--------------|---------------|------------------------------------------------------------|
| POST        | `/api/borrow/{bookId}/patron/{patronId}`  | Allow a patron to borrow a book                | -            | 200, 404      | `curl -X POST http://localhost:8081/api/borrow/1/patron/1` |
| PUT         | `/api/return/{bookId}/patron/{patronId}`  | Record the return of a borrowed book by patron | -            | 200, 404      | `curl -X PUT http://localhost:8081/api/return/1/patron/1`  |

## Authentication (JWT) (In Progress )

### Login to Obtain JWT
To authenticate, users must login using the `/api/auth/login` endpoint to receive a JWT token.

**Login Endpoint**:  
`POST /api/auth/login`

**Request Body**:
```json
{
"username": "admin",
"password": "password"
}

```
```json
{
"token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```
Authorization: Bearer <JWT_TOKEN>

## Error Handling

- **400 Bad Request**: When required fields are missing or invalid.
- **404 Not Found**: When trying to access a resource that doesn’t exist.
- **401 Unauthorized**: If the JWT token is missing or invalid.
- **500 Internal Server Error**: For server-side issues.
## Unit Testing

You can run unit tests using JUnit and Mockito.

To run the tests, use the following Maven command:

[//]: # (### **3. Additional Information:**)

[//]: # (If your system uses any caching or specific transaction management, it may be useful to briefly mention how those features work &#40;especially if you implemented caching using Spring Cache or similar&#41;.)

[//]: # ()
[//]: # (Example:)

[//]: # (```md)

[//]: # (## Caching)

[//]: # ()
[//]: # (The system uses Spring Cache to improve performance by caching frequently accessed data like books.)

[//]: # ()
[//]: # (## Transaction Management)

[//]: # ()
[//]: # (Critical operations, like adding or deleting books, are wrapped in transactions to ensure data integrity.)
[//]: # (## API Documentation &#40;Swagger&#41;)

[//]: # ()
[//]: # (You can view and interact with the API documentation via Swagger UI:)

[//]: # ()
[//]: # (- **URL**: `http://localhost:8081/swagger-ui.html`)
