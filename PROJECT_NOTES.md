# User & Authenticator Microservice - Project Notes

## 1. Project Implementation Sequence

### 1.1. Project Setup
- **Spring Boot** project initialized.
- Dependencies added in `pom.xml`:
  - Spring Boot Starter Web, Data JPA, MySQL Connector, Lombok, Spring Security, jjwt (JWT library), Apache Commons Lang.

### 1.2. Layered Architecture
- **Models**: Represent database entities (`User`, `Role`, `Session`, `SessionStatus`, `BaseModel`).
- **DTOs**: Data Transfer Objects for API communication (`LoginRequestDto`, `SignUpRequestDto`, `LogoutRequestDto`, etc.).
- **Repositories**: JPA interfaces for data access (`UserRepository`, `RoleRepository`, `SessionRepository`).
- **Services**: Business logic (`AuthService`, `UserService`, `RoleService`).
- **Controllers**: REST endpoints (`AuthController`, `UserController`, `RoleController`).

### 1.3. Security & Password Management
- **BCryptPasswordEncoder** is used for password hashing (never store plain passwords).
- Spring Security configuration in `SpringSecurity.java` provides a singleton bean for password encoding.

### 1.4. JWT Authentication
- **JWT (JSON Web Token)** is used for stateless authentication.
- On login:
  - User credentials are verified.
  - If valid, a JWT is generated with user info, roles, creation, and expiry date.
  - JWT is stored in a `Session` entity for tracking multiple logins.
  - JWT is sent to the client as a cookie.
- On logout:
  - The session is marked as ended in the database.
- On token validation:
  - The session and token are checked for validity and expiry.

### 1.5. Session Management
- Each login creates a new session (multiple active sessions per user are possible).
- Sessions are tracked in the database with status (`ACTIVE`, `ENDED`).

### 1.6. Application Properties
- Configured for MySQL, port, and security settings in `application.properties`.

---

## 2. Technical Interview Notes

### 2.1. Why use BCrypt for passwords?
- BCrypt is a strong hashing algorithm with built-in salt, making it resistant to rainbow table and brute-force attacks.
- Passwords are never decrypted; instead, the hash is compared using `matches()`.

### 2.2. Why JWT for authentication?
- JWT allows stateless authentication, meaning the server does not need to store session data for each user.
- The token contains all necessary user info and is signed to prevent tampering.
- JWT structure: **Header** (algorithm info), **Payload** (user data), **Signature** (for verification).

### 2.3. How is multiple login handled?
- Each login generates a new JWT and session record.
- The system can track and limit the number of active sessions per user (future enhancement).

### 2.4. How is logout implemented?
- The session corresponding to the JWT is marked as ended in the database.
- The token is invalidated for future requests.

### 2.5. How is token validation performed?
- The token is parsed and verified using the secret key.
- The session status and expiry are checked.
- If valid, the user is authenticated; otherwise, access is denied.

### 2.6. Security Best Practices
- Passwords are always hashed.
- JWTs are signed and have an expiry.
- Sensitive endpoints are protected.
- Security auto-configuration is customized to avoid default behaviors that may expose vulnerabilities.

---

## 3. Project Flow Diagram

```
[User] --(login/signup)--> [AuthController] --(service)--> [AuthService]
   |                                               |
   |<-- JWT token (cookie) <-----------------------|
   |
   |--(subsequent requests with JWT)--> [validate/logout endpoints]
```

---

## 4. What Has Been Implemented

- User registration and login with secure password storage.
- JWT-based authentication for stateless session management.
- Multiple concurrent sessions per user.
- Logout and token invalidation.
- Role-based user model (extensible for authorization).
- Clean, layered Spring Boot architecture.
- MySQL database integration.
- Security configuration for custom password encoding and JWT handling.

---

## 5. References

- [jwt GitHub](https://github.com/jwtk/jjwt?tab=readme-ov-file#features)
- [Spring Security Docs](https://docs.spring.io/spring-security/site/docs/current/reference/html5/)

---

**This note should help you understand the project's flow, technical decisions, and be ready for interviews or further development!** 