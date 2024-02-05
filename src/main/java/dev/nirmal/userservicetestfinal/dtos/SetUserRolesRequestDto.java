package dev.nirmal.userservicetestfinal.dtos;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SetUserRolesRequestDto {
  private List<Long> roleIds;
}
