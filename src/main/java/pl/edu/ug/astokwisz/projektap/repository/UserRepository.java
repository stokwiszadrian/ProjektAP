package pl.edu.ug.astokwisz.projektap.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.ug.astokwisz.projektap.domain.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findByFirstnameAndLastname (String firstname, String lastname);

    Optional<User> findUserByUsername(String username);

}
