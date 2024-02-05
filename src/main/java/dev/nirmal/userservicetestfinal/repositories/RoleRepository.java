package dev.nirmal.userservicetestfinal.repositories;

import dev.nirmal.userservicetestfinal.models.Role;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

  List<Role> findAllByIdIn(List<Long> roleIds);
}
