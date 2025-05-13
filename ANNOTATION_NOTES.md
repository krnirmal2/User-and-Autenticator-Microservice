# Annotation Notes

## 1. `@Service`
**Where used:**  
- On service classes like `AuthService`, `UserService`, `RoleService`.

**Why/When to use:**  
- Marks a class as a Spring service component.
- Used for business logic and service layer beans.
- Enables automatic detection and dependency injection by Spring.

---

## 2. `@Bean`
**Where used:**  
- On methods in configuration classes (e.g., `SpringSecurity.java`).

**Why/When to use:**  
- Tells Spring that a method instantiates, configures, and returns a bean to be managed by the Spring container.
- Use when you want to provide a custom bean (like `BCryptPasswordEncoder`) for dependency injection.

---

## 3. `@Configuration`
**Where used:**  
- On configuration classes (e.g., `SpringSecurity.java`).

**Why/When to use:**  
- Indicates that the class declares one or more `@Bean` methods.
- Used for Java-based Spring configuration instead of XML.

---

## 4. `@Entity`
**Where used:**  
- On model classes that map to database tables (e.g., `User`, `Role`, `Session`).

**Why/When to use:**  
- Marks a class as a JPA entity.
- Required for ORM (Object-Relational Mapping) with Hibernate/JPA.

---

## 5. `@Id` and `@GeneratedValue`
**Where used:**  
- On the primary key field in entity classes (e.g., `BaseModel`).

**Why/When to use:**  
- `@Id`: Marks the primary key of an entity.
- `@GeneratedValue`: Specifies how the primary key should be generated (e.g., `GenerationType.IDENTITY` for auto-increment).

---

## 6. `@MappedSuperclass`
**Where used:**  
- On base model classes (e.g., `BaseModel`).

**Why/When to use:**  
- Indicates that the class is a superclass for JPA entities.
- Fields in this class are mapped to the database columns in subclasses.

---

## 7. `@Getter` and `@Setter` (Lombok)
**Where used:**  
- On model and DTO classes.

**Why/When to use:**  
- Automatically generates getter and setter methods at compile time.
- Reduces boilerplate code.

---

## 8. `@ManyToMany`, `@ManyToOne`, `@Enumerated`
**Where used:**  
- On entity relationships (e.g., `User.roles`, `Session.user`, `Session.sessionStatus`).

**Why/When to use:**  
- `@ManyToMany`: Defines a many-to-many relationship between entities.
- `@ManyToOne`: Defines a many-to-one relationship.
- `@Enumerated`: Specifies that an enum should be persisted as an ordinal or string.

---

## 9. `@Repository`
**Where used:**  
- On repository interfaces (e.g., `UserRepository`, `SessionRepository`).

**Why/When to use:**  
- Marks the interface as a Spring Data repository.
- Enables exception translation and component scanning.

---

## 10. `@RestController`
**Where used:**  
- On controller classes (e.g., `AuthController`, `RoleController`).

**Why/When to use:**  
- Combines `@Controller` and `@ResponseBody`.
- Used for RESTful web services; all methods return JSON/XML by default.

---

## 11. `@RequestMapping`, `@PostMapping`
**Where used:**  
- On controller classes and methods.

**Why/When to use:**  
- `@RequestMapping`: Maps HTTP requests to handler methods or classes.
- `@PostMapping`: Shortcut for `@RequestMapping(method = RequestMethod.POST)`.

---

## 12. `@RequestBody`
**Where used:**  
- On controller method parameters.

**Why/When to use:**  
- Binds the HTTP request body to a method parameter.
- Used for accepting JSON/XML payloads in REST APIs.

---

**These notes explain the purpose and usage of each annotation as referenced in your codebase with NOTE comments.** 