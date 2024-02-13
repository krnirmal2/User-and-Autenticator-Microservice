package dev.nirmal.userservicetestfinal.security;

import dev.nirmal.userservicetestfinal.models.User;
import dev.nirmal.userservicetestfinal.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomSpringUserDetailsService implements UserDetailsService {
  // NOTE 34 UP: this class use for create our own User
  // custom class to persist the userDetails that is provided by SpringSecurity framwork
  // and we will use our

  UserRepository userRepository;

  public CustomSpringUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) {
    // Fetch the user with the given userName from db
    var optionalUser = userRepository.findByEmail(username);
    if (optionalUser.isEmpty()) {
      throw new UsernameNotFoundException("user with the given username not found");
    }
    User user = optionalUser.get();

    return new CustomUserDetails(user);
  }
}
