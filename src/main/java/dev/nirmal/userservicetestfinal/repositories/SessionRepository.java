package dev.nirmal.userservicetestfinal.repositories;

import dev.nirmal.userservicetestfinal.models.Session;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

  Optional<Session> findByTokenAndUser_Id(String token, Long userId);
  // select * from sessions where token = <> and userId = <>
}
