### 🌐 What is CORS?

**CORS** stands for **Cross-Origin Resource Sharing**.

It is a **security feature in web browsers** that **controls how a web page from one origin can access resources on another origin**.

---

### 🔒 Why is CORS Needed?

By default, the browser **blocks** JavaScript requests from one domain (origin) to another domain for security reasons.

For example:

* **Your frontend (React/Angular)** runs on: `http://localhost:3000`
* **Your backend (Spring Boot)** runs on: `http://localhost:8080`

When frontend tries to call an API like:

```js
fetch("http://localhost:8080/auth/login")
```

The browser **blocks it unless CORS is enabled** on the backend.

---

### ⚙️ How to Enable CORS in Spring Boot?

You need to allow cross-origin requests like this:

```java
@Bean
public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedOrigins("http://localhost:3000")  // Allow frontend origin
                    .allowedMethods("GET", "POST", "PUT", "DELETE")
                    .allowCredentials(true);
        }
    };
}
```

Or in **Spring Security 6+**, inside the `SecurityFilterChain`:

```java
http
    .cors(Customizer.withDefaults())  // Enable CORS
    .authorizeHttpRequests(...);
```

Make sure to also define a `CorsConfigurationSource` bean.

---

### 🧪 Summary

| Term     | Meaning                                                                     |
| -------- | --------------------------------------------------------------------------- |
| CORS     | Mechanism that allows frontend apps from other origins to call backend APIs |
| Default  | Browser blocks calls to different origin (like 3000 → 8080)                 |
| Solution | Backend must explicitly allow it via headers or config                      |

---

Would you like me to add a working CORS config to your Spring Boot app based on your code?
