package dev.nirmal.userservicetestfinal.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dev.nirmal.userservicetestfinal.models.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
@JsonDeserialize(as = CustomGrantedAuthority.class)
@NoArgsConstructor
public class CustomGrantedAuthority implements GrantedAuthority {
  private Role role;

  // NOTE 51 UP : this class is used to implement ganted authority
  // for role of the user should be come in the payload of the token
  // and we customise the role
  public CustomGrantedAuthority(Role role) {
    this.role = role;
  }

  @Override
  @JsonIgnore
  public String getAuthority() {
    return role.getRole();
  }
}
