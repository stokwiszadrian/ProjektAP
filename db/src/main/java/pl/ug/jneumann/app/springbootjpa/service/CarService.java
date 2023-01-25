package pl.ug.jneumann.app.springbootjpa.service;

import org.springframework.stereotype.Service;
import pl.ug.jneumann.app.springbootjpa.domain.Manufacture;
import pl.ug.jneumann.app.springbootjpa.repository.CarRepository;

import pl.ug.jneumann.app.springbootjpa.domain.Car;
import pl.ug.jneumann.app.springbootjpa.repository.ManufactureRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

@Service
public class CarService {

  final
  CarRepository carRepository;

  final ManufactureRepository manufactureRepository;

  public CarService(CarRepository carRepository, ManufactureRepository manufactureRepository) {
    this.carRepository = carRepository;
    this.manufactureRepository = manufactureRepository;
  }

  public void learning() {

    Car car1 = new Car("Ford", 1956);
    Car car2 = new Car("Fiat", 1982);
    Car car3 = new Car("Honda", 1951);
    Car car4 = new Car("Fiat", 1980);

//    Car persistedCar = carRepository.save(car1);
//    carRepository.save(car2);
//    carRepository.save(car3);
//    carRepository.save(car4);

    List<Car> fiats = new ArrayList<>();

    fiats.add(car2);
    fiats.add(car4);

    // Transient
    Manufacture manufacture = new Manufacture("FIAT");
    manufacture.setCars(fiats);

    // Persisted
    Manufacture persistedManufacture = manufactureRepository.save(manufacture);

    manufactureRepository.deleteById(persistedManufacture.getId());



//    System.out.println(persistedCar);
//
//    Optional<Car> optCar = carRepository.findById(persistedCar.getId());
//    optCar.ifPresent(System.out::println);

    List<Car> cars = carRepository.findByMake("Fiat");
    System.out.println(cars);





  }

}
