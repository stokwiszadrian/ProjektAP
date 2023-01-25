package pl.edu.ug.astokwisz.projektap.repository;

import org.springframework.data.repository.CrudRepository;
import pl.edu.ug.astokwisz.projektap.domain.Privilege;

import java.util.Optional;

public interface PrivilegeRepository extends CrudRepository<Privilege, Long> {
    Optional<Privilege> findByName(String name);
}
