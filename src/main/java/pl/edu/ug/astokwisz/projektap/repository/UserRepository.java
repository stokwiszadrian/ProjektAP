package pl.edu.ug.astokwisz.projektap.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.ug.astokwisz.projektap.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}
