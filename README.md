# File Database - RESTful API

## Description

This project provides a simple RESTful API for managing data in a file-based 'database'. The 'database' uses a CSV data format for storage. The API allows users to perform CRUD (Create, Read, Update, Delete) operations on the data.

The main features of the API include:

- Insert a new row into the 'database'.
- Retrieve specific rows or all rows from the 'database'.
- Update a specific row in the 'database'.
- Delete a specific row from the 'database'.

The API is built using Java 17 and Spring Boot, with a custom service to handle file-based storage using CSV format. The use of an embedded database like H2 is intentionally avoided, making it suitable for small-scale data management scenarios.

## Prerequisites

Before running the application, make sure you have the following installed:

- Java 11 or later
- Maven 3.6 or later

## Getting Started

### Running the Application

To run the application, follow these steps:

1. Clone the repository.

2. Change directory to the project folder: 
   - cd your-repo

3. Build the project using Maven: 
   - mvn package

4. Run the JAR file:
   - java -jar target/file-database-0.0.1-SNAPSHOT.jar


**Note:** Before running the application, ensure that the local port 8089 is not already in use by other applications. If port 8089 is occupied, you can either free the port or modify the application's port in the `application.properties` file.

The application will start, and you should see the Spring Boot banner in the console.

5. Access the API documentation:

Once the application is running, you can access the API documentation using Swagger. Open your web browser and navigate to the following URL:
    http://localhost:8089/swagger-ui/index.html


The Swagger UI will be displayed, providing an interactive documentation for the API endpoints. You can explore the available endpoints, send test requests, and view the responses directly from the Swagger UI.


