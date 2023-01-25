package pl.ug.jneumann.app.springbootjpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.ug.jneumann.app.springbootjpa.domain.Manufacture;

@Repository
public interface ManufactureRepository extends CrudRepository<Manufacture, Long> {
}
