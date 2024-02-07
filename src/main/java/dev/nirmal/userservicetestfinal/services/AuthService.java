package dev.nirmal.userservicetestfinal.services;

import dev.nirmal.userservicetestfinal.dtos.UserDto;
import dev.nirmal.userservicetestfinal.models.Role;
import dev.nirmal.userservicetestfinal.models.Session;
import dev.nirmal.userservicetestfinal.models.SessionStatus;
import dev.nirmal.userservicetestfinal.models.User;
import dev.nirmal.userservicetestfinal.repositories.SessionRepository;
import dev.nirmal.userservicetestfinal.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import java.util.*;
import javax.crypto.SecretKey;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
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
    String token = RandomStringUtils.randomAlphanumeric(30);

    // NOTE 16: now we create JWS (json web signature) token
    // with the header + payload + signature for more security

    // Create a test key suitable for the desired HMAC-SHA algorithm:
    MacAlgorithm alg = Jwts.SIG.HS256; // or HS384 or HS256
    SecretKey key = alg.key().build();

    //    String message = "Hello World!"; // message send in the jwt token as payload part
    /*
    // NOTE 18: our own message as payload in side the token
    String message =
        "{\n"
            + "  \"email\": \"harsh@scaler.com\",\n"
            + "  \"roles\": [\n"
            + "    \"student\",\n"
            + "    \"ta\"\n"
            + "  ],\n"
            + "  \"expiry\": \"31stJan2024\"\n"
            + "}";
    */
    // NOTE 19 :
    //    we replace the above string using map
    // JSON -> Key : Value
    Map<String, Object> jsonMap = new HashMap<>();
    jsonMap.put("email", user.getEmail());
    jsonMap.put("roles", List.of(user.getRoles()));
    jsonMap.put("createdAt", new Date());
    jsonMap.put("expiryAt", DateUtils.addDays(new Date(), 30));

    // Create the compact JWS:
    //    String jws = Jwts.builder().content(content, "text/plain").signWith(key, alg).compact();
    String jws = Jwts.builder().claims(jsonMap).signWith(key, alg).compact();
    // Parse the compact JWS:
    /*
    content = Jwts.parser().verifyWith(key).build().parseSignedContent(jws).getPayload();
    assert message.equals(new String(content, StandardCharsets.UTF_8));
    */

    // NOTE 6:
    // create session for each user that how many times the user is loged in and check in the db per
    // user
    // haveing different session with same id
    Session session = new Session();
    session.setSessionStatus(SessionStatus.ACTIVE);
    //    session.setToken(token);
    session.setToken(jws);
    session.setUser(user);
    // session.setExpiringAt(//current time + 30 days);//TODO : HOME WORK
    sessionRepository.save(session);

    UserDto userDto = new UserDto();
    userDto.setEmail(email); // set in db

    // NOTE 7:
    // create the cookie and set the auth token of jws
    MultiValueMapAdapter<String, String> headers = new MultiValueMapAdapter<>(new HashMap<>());
    headers.add(HttpHeaders.SET_COOKIE, "auth-token:" + jws);
    //    headers.add(HttpHeaders.SET_COOKIE, "auth-token:" + token);

    // NOTE 8:
    // send the response dto
    // now call the database and find hit the login API
    // and you are able to see the authToken in the
    ResponseEntity<UserDto> response = new ResponseEntity<>(userDto, headers, HttpStatus.OK);

    return response;
  }

  public ResponseEntity<Void> logout(String token, Long userId) {
    // NOTE 11:
    // we can find the no. of token for particular userId
    Optional<Session> sessionOptional = sessionRepository.findByTokenAndUser_Id(token, userId);

    if (sessionOptional.isEmpty()) {
      return null;
    }

    Session session = sessionOptional.get();

    // NOTE 15:
    //   set the session Status to zero when we are taken the token from db
    // for testing to zero
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

  public SessionStatus validate(
      String token, Long userId) { // NOTE19: this only check a particular token is valid or not
    Optional<Session> sessionOptional = sessionRepository.findByTokenAndUser_Id(token, userId);

    // NOTE 10:
    // validate the token made up of header + body + signature from theory
    if (sessionOptional.isEmpty()) {
      return null;
    }
    /* TODO: TASK
        Task-1 : Implement limit on number of active sessions for a user.
        Task-2 : Implement login workflow using the token details with validation of expiry date.
    */
    /* NOTE 14:
            we have to validate the token from generated by bCrypt algorithm
            using header + body + signature
            and we have to match function to use
            and also token we need to  check
    */

    // NOTE 18:
    // what are token validation need to validate for a particular thing to access
    // that should be present here
    Session session = sessionOptional.get();
    if (!session
        .getSessionStatus()
        .equals(SessionStatus.ACTIVE)) { // if session is not active then return end session
      return SessionStatus.ENDED;
    }

    Date currentTime = new Date();
    if (session.getExpiringAt().before(currentTime)) { // if token is expire
      return SessionStatus.ENDED;
    }

    // if we need to extract the key value pair that is inside the token payload then we can
    // parse the token and find the correponding value like email, roles, date time etc
    Jws<Claims> jwsClaims = Jwts.parser().build().parseSignedClaims(token);
    String email = (String) jwsClaims.getPayload().get("email");
    List<Role> roles = (List<Role>) jwsClaims.getPayload().get("roles");
    Date createdAt = (Date) jwsClaims.getPayload().get("createAt");
    /*
          if(restrictedEmails.contains(email)){
            // reject the token
          }
    */
    return SessionStatus.ACTIVE;
  }
}

/*
NOTE 17:
    below is the JWT token for encrpyt the message = "Hello world"
    header =  eyJjdHkiOiJ0ZXh0L3BsYWluIiwiYWxnIjoiSFMyNTYifQ.
    payload = SGVsbG8gV29ybGQh.
    signature = EHQJBVvni4oDe_NEqnecIwNmOTUe_7Hs_jVW_XT-b1o

*/

/*
Task-1 : Implement limit on number of active sessions for a user.
Task-2 : Implement login workflow using the token details with validation of expiry date.
 */
