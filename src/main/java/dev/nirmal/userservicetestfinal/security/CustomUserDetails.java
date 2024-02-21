package dev.nirmal.userservicetestfinal.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dev.nirmal.userservicetestfinal.models.User;
import java.util.Collection;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@JsonDeserialize(
    as = CustomUserDetails.class) // NOTE 42 UP: used for deserilaise the json from object
public class CustomUserDetails implements UserDetails {
  //    NOTE 34 UP: this class we will modified to provide the
  // different method for getting userDetails
  // we use to get all the details from here

  User user;

  public CustomUserDetails(User user) {
    this.user = user;
  }

  public CustomUserDetails() {}

  @Override
  @JsonIgnore
  public Collection<? extends GrantedAuthority> getAuthorities() {

    return null;
  }

  @Override
  @JsonIgnore // NOTE 45 UP: to ignore this set of variable fetching as we don't require
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  @JsonIgnore
  public String getUsername() {
    return user.getEmail();
  }

  @Override
  @JsonIgnore
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  @JsonIgnore
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  @JsonIgnore
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  @JsonIgnore
  public boolean isEnabled() {
    return true;
  }
}
