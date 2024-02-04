package dev.deepak.userservicetestfinal.services;

import dev.deepak.userservicetestfinal.dtos.UserDto;
import dev.deepak.userservicetestfinal.models.Role;
import dev.deepak.userservicetestfinal.models.Session;
import dev.deepak.userservicetestfinal.models.SessionStatus;
import dev.deepak.userservicetestfinal.models.User;
import dev.deepak.userservicetestfinal.repositories.SessionRepository;
import dev.deepak.userservicetestfinal.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;

@Service
public class AuthService {
  private UserRepository userRepository;
  private SessionRepository sessionRepository;
  // NOTE 2:
  // add BCrypt encoder for encrpyt the passward
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  public AuthService(
      UserRepository userRepository,
      SessionRepository sessionRepository,
      BCryptPasswordEncoder bCryptPasswordEncoder) {
    // NOTE 3: for BCryptPasswordEncoder we need to manually create bin for this
    this.userRepository = userRepository;
    this.sessionRepository = sessionRepository;
    this.bCryptPasswordEncoder =
        bCryptPasswordEncoder; // Injected the dependency with out "new BCryptPasswordEncoder()" we
    // have use @Bean to know spring to know its need to initialise
  }

  public ResponseEntity<UserDto> login(String email, String password) {
    Optional<User> userOptional = userRepository.findByEmail(email);

    // if user doesnot exist
    if (userOptional.isEmpty()) {
      return null;
    }

    User user = userOptional.get();

    if (!bCryptPasswordEncoder.matches(
        password,
        user.getPassword())) { // NOTE 5: we cant use !user.getPassword().equals(password) because
      // it is not possible to decrypt the given string
      // so we use the match function so it will check if the algorithm can generate this password
      // or not
      throw new RuntimeException("Wrong password entered");
    }

    // Generating the token if match
    // String token = RandomStringUtils.randomAlphanumeric(30);

    /*
            // Create a test key suitable for the desired HMAC-SHA algorithm:
            MacAlgorithm alg = Jwts.SIG.HS256; //or HS384 or HS256
            SecretKey key = alg.key().build();

    //        String message = "{\n" +
    //                "  \"email\": \"harsh@scaler.com\",\n" +
    //                "  \"roles\": [\n" +
    //                "    \"student\",\n" +
    //                "    \"ta\"\n" +
    //                "  ],\n" +
    //                "  \"expiry\": \"31stJan2024\"\n" +
    //                "}";
            //JSON -> Key : Value
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("email", user.getEmail());
            jsonMap.put("roles", List.of(user.getRoles()));
            jsonMap.put("createdAt", new Date());
            jsonMap.put("expiryAt", DateUtils.addDays(new Date(), 30));

            //byte[] content = message.getBytes(StandardCharsets.UTF_8);

            // Create the compact JWS:
            //String jws = Jwts.builder().content(content, "text/plain").signWith(key, alg).compact();
            String jws = Jwts.builder()
                    .claims(jsonMap)
                    .signWith(key, alg)
                    .compact();

            // Parse the compact JWS:
            //content = Jwts.parser().verifyWith(key).build().parseSignedContent(jws).getPayload();
            //assert message.equals(new String(content, StandardCharsets.UTF_8));*/

    // NOTE 6:
    // create session fo
    Session session = new Session();
    session.setSessionStatus(SessionStatus.ACTIVE);
    session.setToken(jws);
    session.setUser(user);
    // session.setExpiringAt(//current time + 30 days);
    sessionRepository.save(session);

    UserDto userDto = new UserDto();
    userDto.setEmail(email);

    // NOTE 7:
    // create the cookie and set the auth token of jws
    MultiValueMapAdapter<String, String> headers = new MultiValueMapAdapter<>(new HashMap<>());
    headers.add(HttpHeaders.SET_COOKIE, "auth-token:" + jws);

    // NOTE 8:
    // send the response dto
    // now call the database and find hit the login API
    // and you are able to see the authToken in the
    ResponseEntity<UserDto> response = new ResponseEntity<>(userDto, headers, HttpStatus.OK);

    return response;
  }

  public ResponseEntity<Void> logout(String token, Long userId) {
    Optional<Session> sessionOptional = sessionRepository.findByTokenAndUser_Id(token, userId);

    if (sessionOptional.isEmpty()) {
      return null;
    }

    Session session = sessionOptional.get();

    session.setSessionStatus(SessionStatus.ENDED);

    sessionRepository.save(session);

    return ResponseEntity.ok().build();
  }

  public UserDto signUp(String email, String password) {
    User user = new User();
    user.setEmail(email);
    // NOTE 9:
    // we need to encrypt the passward not the simple  password
    user.setPassword(
        bCryptPasswordEncoder.encode(
            password)); // We should store the encrypted password in the DB for a user.
    // Save to db
    User savedUser = userRepository.save(user);

    return UserDto.from(savedUser);
  }

  public SessionStatus validate(String token, Long userId) {
    Optional<Session> sessionOptional = sessionRepository.findByTokenAndUser_Id(token, userId);

    if (sessionOptional.isEmpty()) {
      return null;
    }

    Session session = sessionOptional.get();

    if (!session.getSessionStatus().equals(SessionStatus.ACTIVE)) {
      return SessionStatus.ENDED;
    }

    Date currentTime = new Date();
    if (session.getExpiringAt().before(currentTime)) {
      return SessionStatus.ENDED;
    }

    // JWT Decoding.
    Jws<Claims> jwsClaims = Jwts.parser().build().parseSignedClaims(token);

    // Map<String, Object> -> Payload object or JSON
    String email = (String) jwsClaims.getPayload().get("email");
    List<Role> roles = (List<Role>) jwsClaims.getPayload().get("roles");
    Date createdAt = (Date) jwsClaims.getPayload().get("createdAt");

    //        if (restrictedEmails.contains(email)) {
    //            //reject the token
    //        }

    return SessionStatus.ACTIVE;
  }
}

/*

eyJjdHkiOiJ0ZXh0L3BsYWluIiwiYWxnIjoiSFMyNTYifQ.
SGVsbG8gV29ybGQh.
EHQJBVvni4oDe_NEqnecIwNmOTUe_7Hs_jVW_XT-b1o

*/

/*
Task-1 : Implement limit on number of active sessions for a user.
Task-2 : Implement login workflow using the token details with validation of expiry date.
 */
