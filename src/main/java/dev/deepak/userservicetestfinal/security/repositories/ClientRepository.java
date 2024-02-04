package dev.deepak.userservicetestfinal.security.repositories;

import dev.deepak.userservicetestfinal.security.models.Client;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
  Optional<Client> findByClientId(String clientId);
}
