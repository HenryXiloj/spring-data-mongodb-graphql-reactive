# Spring Data MongoDB GraphQL Reactive

A reactive Spring Boot application demonstrating the integration of Spring Data MongoDB with GraphQL using reactive programming principles. This project showcases how to build modern, non-blocking web applications with real-time data operations.

📘 Blog Post: [Spring Data Reactive With MongoDB and GrahpQL](https://jarmx.blogspot.com/2023/07/spring-data-reactive-with-mongodb-and.html)

## 📋 Table of Contents

- [Overview](#overview)
- [Technologies](#technologies)
- [Architecture](#architecture)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [API Schema](#api-schema)
- [Usage](#usage)
- [Testing](#testing)
- [Project Structure](#project-structure)
- [Contributing](#contributing)

## 🚀 Overview

This application demonstrates a fully reactive stack implementation using:
- **Spring Boot Reactive** for building non-blocking applications
- **MongoDB** as the reactive NoSQL database
- **GraphQL** for flexible API queries and mutations
- **Docker** for containerized database deployment

The project implements a Category management system with full CRUD operations, showcasing reactive streams with `Flux` and `Mono` types.

## 🛠 Technologies

| Technology | Version | Purpose |
|------------|---------|---------|
| Spring Boot | 3.1.1 | Application framework |
| Java | 17 | Programming language |
| MongoDB | Latest | NoSQL database |
| GraphQL | - | API query language |
| Docker | - | Database containerization |
| Maven | - | Build tool |
| Project Lombok | - | Code generation |
| Spring WebFlux | - | Reactive web framework |

## 🏗 Architecture

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   GraphQL       │    │   Service       │    │   MongoDB       │
│   Controller    │───▶│   Layer         │───▶│   Repository    │
└─────────────────┘    └─────────────────┘    └─────────────────┘
         │                       │                       │
         ▼                       ▼                       ▼
    Flux/Mono              Reactive Streams         Reactive Mongo
```

## 📋 Prerequisites

Before running this application, ensure you have:

- **Java 17** or higher
- **Maven 3.6+**
- **Docker & Docker Compose**
- **IDE** (IntelliJ IDEA recommended)

## 🔧 Installation

### 1. Clone the Repository

```bash
git clone <repository-url>
cd spring-data-mongodb-graphql-reactive
```

### 2. Start MongoDB with Docker

Create a `docker-compose-mongodb.yml` file:

```yaml
version: "2.0"
services:
  mongodb_container:
    image: mongo:latest
    environment:
      MONGO_INITDB_ROOT_USERNAME: test
      MONGO_INITDB_ROOT_PASSWORD: test
      MONGO_INITDB_DATABASE: tech_notes
    ports:
      - 27017:27017
    volumes:
      - mongodb_data_container:/data/db

volumes:
  mongodb_data_container:
```

Start MongoDB:
```bash
docker-compose -f docker-compose-mongodb.yml up -d
```

### 3. Build and Run

```bash
# Build the project
mvn clean compile

# Run the application
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## ⚙ Configuration

### Application Properties (`application.yml`)

```yaml
# GraphQL Configuration
graphql:
  graphiql:
    enabled: true

# MongoDB Configuration  
spring:
  data:
    mongodb:
      uri: mongodb://test:test@localhost:27017/tech_notes?authSource=admin
```

### CORS Configuration

The application includes CORS configuration to allow cross-origin requests:

```java
@Configuration
@EnableWebFlux
public class WebConfig implements WebFluxConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
            .allowedOrigins("*")
            .maxAge(3600);
    }
}
```

## 📊 API Schema

### GraphQL Schema (`schema.graphqls`)

```graphql
type Query {
    getAllCategories: [Category]
    getCategoryById(id: ID): Category
}

type Mutation {
    createCategory(input: CategoryInput): Category
    updateCategory(id: ID, input: CategoryInput): Category
    deleteCategory(id: ID): Boolean
    deleteAllCategories: Boolean
}

type Category {
    id: ID
    title: String
    posts: [String]
}

input CategoryInput {
    title: String
    posts: [String]
}
```

## 🔍 Usage

### GraphQL Playground

Access GraphiQL interface at: `http://localhost:8080/graphiql`

### API Endpoints

**Base URL:** `http://localhost:8080/graphql`

### Sample Queries

#### Get All Categories
```graphql
query {
  getAllCategories {
    id
    title
    posts
  }
}
```

#### Get Category by ID
```graphql
query {
  getCategoryById(id: "1") {
    id
    title
    posts
  }
}
```

#### Create Category
```graphql
mutation {
  createCategory(input: {
    title: "Technology"
    posts: ["Spring Boot", "GraphQL", "MongoDB"]
  }) {
    id
    title
    posts
  }
}
```

#### Update Category
```graphql
mutation {
  updateCategory(id: "1", input: {
    title: "Updated Technology"
    posts: ["Spring Boot", "GraphQL", "MongoDB", "React"]
  }) {
    id
    title
    posts
  }
}
```

#### Delete Category
```graphql
mutation {
  deleteCategory(id: "1")
}
```

#### Delete All Categories
```graphql
mutation {
  deleteAllCategories
}
```

## 🧪 Testing

### Using Postman

1. Set request method to `POST`
2. URL: `http://localhost:8080/graphql`
3. Headers: `Content-Type: application/json`
4. Body: Include your GraphQL query in JSON format:

```json
{
  "query": "query { getAllCategories { id title posts } }"
}
```

### Using cURL

```bash
curl -X POST \
  http://localhost:8080/graphql \
  -H 'Content-Type: application/json' \
  -d '{
    "query": "query { getAllCategories { id title posts } }"
  }'
```

## 📁 Project Structure

```
src/
├── main/
│   ├── java/com/henry/
│   │   ├── config/
│   │   │   └── WebConfig.java
│   │   ├── controller/
│   │   │   └── CategoryController.java
│   │   ├── model/
│   │   │   ├── Category.java
│   │   │   └── Sequence.java
│   │   ├── repository/
│   │   │   ├── CategoryRepository.java
│   │   │   └── SequenceRepository.java
│   │   ├── resolver/
│   │   │   └── CategoryResolver.java
│   │   ├── service/
│   │   │   └── CategoryService.java
│   │   └── Application.java
│   └── resources/
│       ├── graphql/
│       │   └── schema.graphqls
│       └── application.yml
└── test/
```

## 🔧 Key Features

- **Reactive Programming**: Non-blocking I/O operations using Reactor
- **Flexible Queries**: GraphQL allows clients to request exactly what they need
- **Auto-incrementing IDs**: Custom sequence generation for MongoDB documents
- **CORS Support**: Cross-origin resource sharing enabled
- **Docker Integration**: Easy database setup with Docker Compose
- **GraphiQL Interface**: Built-in query explorer and documentation


## 📚 Additional Resources

- [Spring Data Reactive With MongoDB and GraphQL - Blog Post](https://jarmx.blogspot.com/2023/07/spring-data-reactive-with-mongodb-and.html)
- [Spring Boot Reference Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- [Spring Data MongoDB Reference](https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/)
- [GraphQL Java Documentation](https://www.graphql-java.com/documentation/latest/)
- [Project Reactor Reference](https://projectreactor.io/docs/core/release/reference/)

---

**Note**: This project demonstrates reactive programming principles with Spring Boot. For production use, consider adding proper error handling, validation, authentication, and comprehensive testing.
