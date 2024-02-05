package dev.nirmal.userservicetestfinal.security;

import dev.nirmal.userservicetestfinal.models.User;
import dev.nirmal.userservicetestfinal.repositories.UserRepository;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomSpringUserDetailsService implements UserDetailsService {
  private UserRepository userRepository;

  public CustomSpringUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // Fetch the user with the given username from DB.
    Optional<User> optionalUser = userRepository.findByEmail(username);

    if (optionalUser.isEmpty()) {
      throw new UsernameNotFoundException("User with the given username doesn't exist");
    }

    //        User user = userRepository.findByEmail(username).
    //                orElseThrow(() -> {throw new UsernameNotFoundException("User with the given
    // username doesn't exist");});

    User user = optionalUser.get();
    return new CustomUserDetails(user);
  }
}
