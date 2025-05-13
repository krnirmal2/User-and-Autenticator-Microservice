# High-Level Design (HLD) Notes

## 1. Architecture Overview
- **Type:** Microservice (Spring Boot)
- **Layers:**
  - Controller (API layer)
  - Service (Business logic)
  - Repository (Data access)
  - Model/Entity (Database mapping)
  - Security (Authentication/Authorization)

### Architecture Diagram

```
+-------------------+         +-------------------+         +-------------------+
|                   |         |                   |         |                   |
|   Client (User)   +-------->+   Controller      +-------->+    Service        |
|                   |  REST   |   (API Layer)     |  Calls  |   (Business Logic)|
+-------------------+         +-------------------+         +-------------------+
                                                                |
                                                                v
                                                    +-----------------------+
                                                    |    Repository         |
                                                    |   (Data Access)       |
                                                    +-----------------------+
                                                                |
                                                                v
                                                    +-----------------------+
                                                    |   Database (MySQL)    |
                                                    +-----------------------+

Security Layer (Spring Security, JWT) applies across Controller and Service layers.
```

## 2. Main Components
- **User Management:** Handles user registration, login, and role assignment.
- **Authentication:** Uses JWT for stateless authentication and session management.
- **Session Tracking:** Each login creates a session, tracked in the database.
- **Role Management:** Supports multiple roles per user.
- **Security:** Passwords hashed with BCrypt, JWT tokens for API security.

## 3. Technology Stack
- **Spring Boot:** Application framework
- **Spring Data JPA:** ORM and database access
- **MySQL:** Relational database
- **Spring Security:** Security configuration and password encoding
- **jjwt:** JWT token generation and validation
- **Lombok:** Reduces boilerplate code

## 4. Data Flow (High Level)
1. **User Signup/Login:**
   - User sends credentials to API.
   - Service validates and processes request.
   - On login, JWT token is generated and returned.
2. **Session Management:**
   - Each login creates a new session record in the database.
   - Multiple sessions per user are supported.
3. **Token Validation:**
   - API endpoints validate JWT tokens for authentication.
   - Session status and expiry are checked.
4. **Logout:**
   - Session is marked as ended in the database.

## 5. Security Considerations
- Passwords are never stored in plain text.
- JWT tokens are signed and have expiry.
- Sensitive endpoints are protected by authentication.
- Security auto-configuration is customized as needed.

## 6. Extensibility
- Role-based access control can be extended for authorization.
- Session limits and expiry logic can be enhanced.
- Microservice can be integrated with other services via REST APIs.

## 7. Deployment
- Runs as a standalone Spring Boot application.
- Connects to a MySQL database instance.
- Exposes RESTful endpoints for user and authentication operations. 