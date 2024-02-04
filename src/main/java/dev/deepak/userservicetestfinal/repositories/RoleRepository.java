package dev.deepak.userservicetestfinal.repositories;

import dev.deepak.userservicetestfinal.models.Role;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

  List<Role> findAllByIdIn(List<Long> roleIds);
}
