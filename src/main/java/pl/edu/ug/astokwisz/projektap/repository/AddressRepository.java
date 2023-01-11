package pl.edu.ug.astokwisz.projektap.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends CrudRepository<Adress, Long> {

}
