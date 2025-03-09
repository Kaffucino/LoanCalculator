# Loan Calculator API

A Spring Boot-based Loan Calculator application that provides 
functionalities to calculate loan payments, interest, and other 
related details. This project serves as a simple and 
well-structured backend for loan-related calculations.

## Features

- RESTful API for loan calculation
- Easy to deploy with Docker
- Built using Java 17 and Spring Boot
- Maven for dependency management

# Getting Started
Follow these instructions to set up, run, and contribute to this 
project.

### Prerequisites

Ensure the following tools are installed on your local machine:
- **Java 17** or later
- **Maven 3.8+**
- **Docker** (optional, for containerized deployments)
- Any IDE (e.g., IntelliJ)

---

### Running Locally

1. **Clone the Repository**


2. **Build the Project**:
   Use Maven to build the project:
   ```bash
   mvn clean package
   ```

3. **Run the Application**:
   Run the Spring Boot application:
   ```bash
   java -jar target/LoanCalculatorApplication.jar
   ```


4. **Access the Application**:
   The application will start on port `8080` by default. Open your browser and go to:
   ```
   http://localhost:8080
   ```

---

## Using Docker

### Build and Run with Docker

1. **Build the Docker Image**:
   ```bash
   docker build -t loancalculator-app .
   ```

2. **Run the Docker Container**:
   ```bash
   docker run -p 8080:8080 loancalculator-app
   ```

3. **Access the Application**:
   Visit:
   ```
   http://localhost:8080
   ```
## APIs Documentation

API documentation can be found on
[swagger-ui](http://localhost:8080/swagger-ui/index.html) .

## TODO

* Code coverage with more test cases.
* Add REST Docs in order to improve documentation.
