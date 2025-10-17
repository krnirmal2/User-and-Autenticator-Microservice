package dev.nirmal.userservicetestfinal.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// This class helps to create the object of "BCryptPasswordEncoder" class to use it methods in our
// own project As "BCryptPasswordEncoder" is not our build class so here
// @configuration help s to create the bean of the this class during intiallisation and IOC handle this class bean
// we will get the object from application context which is mapped for each class and its object

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
