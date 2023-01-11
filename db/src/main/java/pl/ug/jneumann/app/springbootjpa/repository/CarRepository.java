package pl.ug.jneumann.app.springbootjpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.ug.jneumann.app.springbootjpa.domain.Car;

import java.util.List;

@Repository
public interface CarRepository extends CrudRepository<Car,Long> {

  List<Car> findByMake(String make);
}
