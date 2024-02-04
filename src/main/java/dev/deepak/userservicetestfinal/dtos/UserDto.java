package dev.deepak.userservicetestfinal.dtos;

import dev.deepak.userservicetestfinal.models.Role;
import dev.deepak.userservicetestfinal.models.User;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
  private String email;
  private Set<Role> roles = new HashSet<>();

  public static UserDto from(User user) {
    UserDto userDto = new UserDto();
    userDto.setEmail(user.getEmail());
    userDto.setRoles(user.getRoles());

    return userDto;
  }
}
