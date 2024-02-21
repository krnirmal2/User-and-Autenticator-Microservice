package dev.nirmal.userservicetestfinal.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@JsonDeserialize(as = User.class) // NOTE 44 UP : used to deserialise the user class as
public class User extends BaseModel {
  private String email;
  private String password;

  @ManyToMany(
      fetch =
          jakarta.persistence.FetchType
              .EAGER) // Note 46 UP ; this will help to resolve error when we authenticate the token
  // as it is by default lazy fetch
  @JsonIgnore // add to ignore this
  private Set<Role> roles = new HashSet<>();
}
