package pl.edu.ug.astokwisz.projektap.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.ug.astokwisz.projektap.domain.ItemType;

@Repository
public interface ItemTypeRepository extends CrudRepository<ItemType, Long> {

}
