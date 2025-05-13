# Low-Level Design (LLD) Notes

## 1. Class Structure

### Class Diagram
```
+----------------+        +----------------+        +----------------+
|    User        |<>-----*|    Role        |
+----------------+        +----------------+
| - id           |        | - id           |
| - email        |        | - role         |
| - password     |        +----------------+
| - roles        |
+----------------+
      |
      | 1
      |         *
+----------------+
|   Session      |
+----------------+
| - id           |
| - token        |
| - expiringAt   |
| - sessionStatus|
| - user         |
+----------------+
      |
      | 1
      |         1
+---------------------+
|  SessionStatus (Enum)|
+---------------------+
| - ACTIVE             |
| - ENDED              |
+---------------------+

DTOs, Services, Controllers, and Repositories interact with these entities as per the flow.
```

### Models (Entities)
- **User**: Represents a user with fields for email, password, and roles.
- **Role**: Represents a user role (e.g., admin, user).
- **Session**: Represents a login session, stores JWT token, expiry, user, and session status.
- **SessionStatus**: Enum for session state (ACTIVE, ENDED).
- **BaseModel**: Common base for entities, provides `id` field.

### DTOs (Data Transfer Objects)
- **LoginRequestDto**: For login requests (email, password).
- **SignUpRequestDto**: For signup requests (email, password).
- **LogoutRequestDto**: For logout requests (token, userId).
- **UserDto**: For user data in responses.
- **ValidateTokenRequestDto**: For token validation requests.
- **CreateRoleRequestDto**: For creating new roles.
- **SetUserRolesRequestDto**: For assigning roles to users.

### Repositories
- **UserRepository**: JPA repository for User.
- **RoleRepository**: JPA repository for Role.
- **SessionRepository**: JPA repository for Session.

### Services
- **AuthService**: Handles authentication, session, and token logic.
- **UserService**: Handles user data and role assignment.
- **RoleService**: Handles role creation and management.

### Controllers
- **AuthController**: Endpoints for login, logout, signup, and token validation.
- **UserController**: Endpoints for user-related operations.
- **RoleController**: Endpoints for role management.

### Security
- **SpringSecurity**: Configures password encoder bean.

## 2. Relationships
- **User <-> Role**: Many-to-many (a user can have multiple roles, a role can belong to multiple users).
- **User <-> Session**: One-to-many (a user can have multiple sessions).
- **Session <-> SessionStatus**: Enum field in Session.

## 3. Method Responsibilities
- **AuthService**
  - `login(email, password)`: Authenticates user, generates JWT, creates session.
  - `logout(token, userId)`: Ends session for the given token and user.
  - `signUp(email, password)`: Registers a new user with encrypted password.
  - `validate(token, userId)`: Validates session and token.
- **UserService**
  - `getUserDetails(userId)`: Fetches user details.
  - `setUserRoles(userId, roleIds)`: Assigns roles to a user.
- **RoleService**
  - `createRole(name)`: Creates a new role.

## 4. Security
- Passwords are hashed using BCrypt.
- JWT tokens are generated and validated for authentication.
- Sessions are tracked in the database for each login.

## 5. Data Flow Example
1. User sends login request.
2. `AuthController` calls `AuthService.login()`.
3. `AuthService` authenticates, generates JWT, creates session, returns token.
4. User uses JWT for subsequent requests.
5. Logout and token validation handled via respective endpoints and service methods. 