# 💳 Payment Management System
A backend REST API built with Spring Boot for managing payments and their lifecycle.
This project implements structured business rules for payment status transitions, historical tracking of events, global exception handling, input validation, and database versioning using Flyway.
The system allows creating, updating, paying, cancelling and refunding payments, while maintaining a consistent record of status changes.

## 🏗 Architecture & Design
### Architecture
The project follows a layered architecture pattern:
- Controller Layer → Handles HTTP requests and responses.
- Service Layer → Contains business logic and status transition rules.
- Repository Layer → Handles data persistence using Spring Data JPA.
- DTO Layer → Separates API contracts from domain entities.
- Mapper Layer → Uses MapStruct to convert between entities and DTOs.
- Exception Handling Layer → Centralized error handling using @ControllerAdvice.

### Business Rules
The system enforces controlled payment status transitions:
- PENDING → PAID
- PENDING → CANCELLED
- PAID → CANCELLED (via refund)
Invalid transitions trigger custom exceptions.

## 🛠 Technologies Used
- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- MySQL
- Flyway (Database Migration)
- MapStruct
- Jakarta Bean Validation
- Maven

## ⚙️ How to Run the Project
### 1️⃣ Clone the repository
- **git clone** https://github.com/leoferreira9/payment-management-system.git
- **cd** payment-management-system
### 2️⃣ Create the database
Create a MySQL database with the following name: 
``` 
CREATE DATABASE payment_management;
```
### 3️⃣ Configure database credentials

Rename the file:
```
application-example.properties
```
to:
```
application.properties
```
Then update the database username and password:
```
spring.datasource.username=USERNAME_HERE
spring.datasource.password=PASSWORD_HERE
```
### 4️⃣ Run the application
```
mvn spring-boot:run
```
## 📊 Domain Design
### Payment
Represents the main payment entity, containing:
- Amount
- Description
- Current status (PENDING, PAID, CANCELLED)
- Creation date

### PaymentRecord
Each payment maintains a history of status changes through PaymentRecord.
Every important action (creation, payment, cancellation, refund) generates a record entry, ensuring:

- Historical tracking of events
- Traceability
- Clear lifecycle management
  
This design separates the current state (Payment) from the historical log (PaymentRecord), improving maintainability and scalability.

## 📘 API Documentation
After running the application, the API documentation is available at:
```
http://localhost:8080/swagger-ui/index.html
```
The Swagger UI provides complete documentation of all available endpoints, request bodies, response structures, and status codes.
