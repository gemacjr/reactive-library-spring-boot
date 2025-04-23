# Spring Boot Reactive GraphQL Library API

A reactive Spring Boot application that provides a GraphQL API for managing a library book system. This project demonstrates CRUD operations using reactive programming paradigms with Spring WebFlux and R2DBC.

## Technologies Used

- **Spring Boot 3.2.0**: Application framework
- **Spring WebFlux**: Reactive web framework
- **Spring GraphQL**: GraphQL implementation
- **Spring Data R2DBC**: Reactive database access
- **H2 Database**: In-memory database
- **Project Lombok**: Reduces boilerplate code
- **JUnit & Reactor Test**: Testing frameworks
- **Gradle**: Build tool

## Getting Started

### Prerequisites

- Java 17 or higher
- Gradle 7.6+ or compatible build tool

### Running the Application

1. Clone the repository
2. Navigate to the project directory
3. Run the application:
```bash
./gradlew bootRun
```

The application will start and be available at http://localhost:8080.

### GraphQL Interface

A GraphiQL interface is available at: http://localhost:8080/graphiql

## API Endpoints

The application exposes the following GraphQL operations:

### Queries

1. **books**: Retrieve all books
```graphql
query {
  books {
    id
    title
    author
    isbn
    publishYear
    genre
    available
  }
}
```

2. **bookById**: Retrieve a book by ID
```graphql
query {
  bookById(id: "1") {
    id
    title
    author
    isbn
  }
}
```

3. **booksByAuthor**: Retrieve books by author
```graphql
query {
  booksByAuthor(author: "J.K. Rowling") {
    id
    title
    isbn
  }
}
```

4. **booksByGenre**: Retrieve books by genre
```graphql
query {
  booksByGenre(genre: "Fantasy") {
    id
    title
    author
  }
}
```

### Mutations

1. **createBook**: Add a new book
```graphql
mutation {
  createBook(book: {
    title: "The Hobbit"
    author: "J.R.R. Tolkien"
    isbn: "978-0547928227"
    publishYear: 1937
    genre: "Fantasy"
    available: true
  }) {
    id
    title
    author
  }
}
```

2. **updateBook**: Update an existing book
```graphql
mutation {
  updateBook(book: {
    id: "1"
    title: "Updated Title"
    available: false
  }) {
    id
    title
    available
  }
}
```

3. **deleteBook**: Delete a book
```graphql
mutation {
  deleteBook(id: "1")
}
```

## Project Structure

- **model**: Contains the Book entity
- **repository**: Contains the reactive repository for database operations
- **service**: Contains the business logic
- **controller**: Contains the GraphQL controllers
- **exception**: Contains custom exceptions and exception handlers
- **config**: Contains configuration classes

## Reactive Programming Features

This project demonstrates several reactive programming concepts:

- Non-blocking database operations with R2DBC
- Reactive streams with Project Reactor (Flux and Mono)
- Reactive error handling with Mono.error() and switchIfEmpty()
- Proper transaction management for write operations

## Best Practices Implemented

1. **Layered Architecture**: Clear separation between controllers, services, and repositories
2. **Exception Handling**: Custom exceptions with a global exception handler
3. **Input Validation**: Bean validation for entity fields
4. **Logging**: Comprehensive logging of operations and errors
5. **Transactional Management**: Proper transaction boundaries
6. **Schema Design**: Well-structured GraphQL schema with input types
7. **Response Types**: Using Flux for collections and Mono for single objects
8. **Builder Pattern**: Used for creating entities
9. **Immutable DTOs**: Using records for input data
10. **Readable Code**: Descriptive method names and comments

## Gradle Build Commands

```bash
# Build the project
./gradlew build

# Run tests
./gradlew test

# Clean build
./gradlew clean build

# Run the application
./gradlew bootRun
```