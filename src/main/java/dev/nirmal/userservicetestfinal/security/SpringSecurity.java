package dev.nirmal.userservicetestfinal.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// This class helps to create the object of "BCryptPasswordEncoder" class to use it methods in our
// own project

@Configuration // NOTE 4: this is tell spring to it is a special class which initialise all the
// required things for spring security
public class SpringSecurity {
  // BCryptPasswordEncoder()"
  // This is SingleTon class by default will spring initialise a single object
  @Bean // automacaly this class intantiate in the caller class when it is call with out "new
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
