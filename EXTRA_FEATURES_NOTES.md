# Extra Features & Configurations Added in This Branch

This branch introduces several advanced features and configurations to enhance scalability, security, and maintainability. Below is a summary of the key additions:

---

## 1. Database Migration Scripts for OAuth2
- **Location:** `src/main/resources/db/migration/`
- **Purpose:** Enables versioned, automated schema management using tools like Flyway or Liquibase.
- **Details:**
  - `V1__.sql` creates tables for OAuth2 client registration, authorization, and consent management.
  - `V2__.sql` refines the client table structure for better alignment with OAuth2 standards or library updates.
- **Benefit:** Supports secure, standards-based authentication and authorization flows (SSO, third-party logins, etc.) and enables stateless scaling of the auth service.

## 2. Eureka Service Discovery Configuration
- **Properties in `application.properties`:**
  - `eureka.client.register-with-eureka=true`
  - `eureka.client.fetch-registry=true`
  - `eureka.client.service-url.defaultZone=http://localhost:8761/eureka`
- **Purpose:** Allows the service to register with a Eureka server for dynamic discovery and load balancing in a microservice ecosystem.
- **Benefit:** Improves resilience, scalability, and flexibility of service-to-service communication.

## 3. Improved Error Reporting
- **Properties in `application.properties`:**
  - `server.error.include-exception=true`
  - `server.error.include-stacktrace=always`
- **Purpose:** Ensures detailed error information is available in server responses for debugging and monitoring.
- **Benefit:** Facilitates faster troubleshooting and better observability during development and testing.

## 4. Dynamic Port Configuration
- **Property:** `server.port=${SERVERPORT}`
- **Purpose:** Allows the server port to be set via environment variable, supporting flexible deployment scenarios (e.g., Docker, cloud environments).

## 5. Security and Logging Enhancements
- **Security auto-configuration exclusion:**
  - `spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration`
- **Security logging:**
  - `logging.level.org.springframework.security=trace`
- **Purpose:** Provides more control over security configuration and detailed logging for security-related events.

## 6. Error Page Customization
- **File:** `src/main/resources/error.html`
- **Purpose:** Customizes the error page shown to users for a better user experience.

## 7. Dockerfile for Containerization
- **File:** `Dockerfile`
- **Purpose:** Enables containerization of the application for consistent deployment across environments.
- **Details:**
  - Uses the OpenJDK base image.
  - Exposes port 8080 (can be overridden by environment variable).
  - Copies the built JAR file from the `target` directory into the container.
  - Runs the application using `java -jar`.
- **Benefit:** Simplifies deployment, supports cloud-native and microservice architectures, and ensures environment consistency.

---

**Summary:**
- These features collectively make the project more production-ready, scalable, and easier to maintain.
- They demonstrate best practices for microservice architecture, secure authentication, robust configuration management, and modern deployment strategies.

**Use these notes to highlight your experience with advanced Spring Boot, OAuth2, Docker, and microservice patterns in interviews or documentation!** 