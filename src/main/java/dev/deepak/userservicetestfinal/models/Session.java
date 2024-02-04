package dev.deepak.userservicetestfinal.models;

import jakarta.persistence.*;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Session extends BaseModel {
  private String token;
  private Date expiringAt;
  @ManyToOne private User user;

  @Enumerated(EnumType.ORDINAL)
  private SessionStatus sessionStatus;
}
