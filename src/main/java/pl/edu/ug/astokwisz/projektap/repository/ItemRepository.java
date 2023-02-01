package pl.edu.ug.astokwisz.projektap.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.ug.astokwisz.projektap.domain.Item;
import pl.edu.ug.astokwisz.projektap.domain.ItemType;
import pl.edu.ug.astokwisz.projektap.domain.User;

import java.util.List;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {

    List<Item> findByItemtype (ItemType itemtype);

    List<Item> findByReservedBy (User reservedBy);


//    List<Item> FindAllByOrderByNameAsc();

//    List<Item> FindByReservedby(User reservedBy);


}
