Here’s a comprehensive `README.md` file for your application:

---

# Customer Management Application

## Overview

This project is a full-stack Customer Management Application built using a Spring Boot backend and a JavaScript frontend. The application allows users to manage customer data by providing functionalities for adding, updating, deleting, and viewing customers. The application also integrates data from an external API and handles CORS issues by proxying requests through the backend.

## Features

- **Customer CRUD Operations**: Add, update, delete, and view customer records.
- **Pagination and Sorting**: Easily navigate through pages of customer data with sorting options.
- **Search Functionality**: Filter customers based on various fields like first name, last name, and email.
- **External API Integration**: Fetch customer data from an external API and sync it with the application's database.
- **CORS Handling**: Proxy requests through the backend to handle CORS restrictions.

## Project Structure

### Backend (Spring Boot)

- **Language**: Java
- **Framework**: Spring Boot
- **Dependencies**: 
  - Spring Web
  - Spring Data JPA
  - MySQL Driver
  - RestTemplate
- **Primary Features**:
  - RESTful API endpoints for managing customer data.
  - Integration with external APIs for customer data synchronization.
  - Handles CORS issues by acting as a proxy for external API requests.

### Frontend

- **Language**: JavaScript, HTML, CSS
- **Framework**: Vanilla JavaScript
- **Primary Features**:
  - Dynamic customer management interface.
  - Filtering, sorting, and pagination of customer data.
  - Form-based interactions for creating and updating customers.
  - Pagination controls with page navigation.

## Prerequisites

- **Backend**:
  - JDK 11 or higher
  - Maven
  - MySQL (or any preferred database)
  
- **Frontend**:
  - Modern web browser (Google Chrome, Firefox, etc.)

## Setup and Installation

### Backend

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/Akhiltop/customercrud.git
   cd employee
   ```

2. **Set Up the Database**:
   - Create a MySQL database (e.g., `employeecrud`).
   - Update `src/main/resources/application.properties` with your database credentials:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/employeecrud
     spring.datasource.username=root
     spring.datasource.password=yourpassword
     spring.jpa.hibernate.ddl-auto=update
     spring.jpa.show-sql=true
     ```

3. **Run the Backend**:
   - Build and run the Spring Boot application:
     ```bash
     mvn clean install
     mvn spring-boot:run
     ```
   - The backend server should now be running on `http://localhost:8080`.

### Frontend

1. **Navigate to the Frontend Directory**:
   ```bash
   cd ./frontend
   ```

2. **Open the HTML File**:
   - Open `index.html` in your preferred web browser.

3. **Interact with the Application**:
   - Use the provided interface to manage customers.
   - Use the "Sync Customers" button to fetch and sync data from the external API.
   - Utilize the pagination, sorting, and filtering features to view and manage customer data.

## Interactions Between Frontend and Backend

- **Customer CRUD Operations**: The frontend interacts with the backend's RESTful API to perform create, read, update, and delete operations on customer data.
- **Data Fetching and Syncing**: The frontend can trigger a sync operation that fetches customer data from an external API. This data is then sent to the backend, where it is either updated or inserted into the database.
- **Pagination and Sorting**: The frontend sends requests with pagination and sorting parameters to the backend, which processes the request and returns the appropriate customer data.
- **CORS Handling**: All external API requests are proxied through the backend to handle CORS restrictions, ensuring smooth data integration.

## API Endpoints

- **GET /api/customers**: Fetch all customers.
- **POST /api/customers/sync**: Sync customer data from the external API.
- **POST /api/customers**: Add a new customer.
- **GET /api/customers/{id}**: Gets an existing customer.
- **PUT /api/customers/{id}**: Update an existing customer.
- **DELETE /api/customers/{id}**: Delete a customer.
- **GET /api/customers/all**: Fetch a list of customers with optional search, sorting, and pagination.

## Conclusion

This Customer Management Application provides a robust interface for managing customer data, with backend support for data integration from external sources and frontend capabilities for rich user interactions. The application is built to handle various user needs, including search, sorting, and pagination, making it a complete solution for customer management.
