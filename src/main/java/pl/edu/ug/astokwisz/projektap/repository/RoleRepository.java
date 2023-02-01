package pl.edu.ug.astokwisz.projektap.repository;

import org.springframework.data.repository.CrudRepository;
import pl.edu.ug.astokwisz.projektap.domain.Role;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
