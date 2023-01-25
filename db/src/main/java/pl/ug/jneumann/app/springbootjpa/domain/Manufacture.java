package pl.ug.jneumann.app.springbootjpa.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Manufacture {

  private Long id;
  private String name;
  private String country;

  private List<Car> cars;

  public Manufacture(String name) {
    this.name = name;
  }

  public Manufacture() {
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @OneToMany(cascade = CascadeType.PERSIST)
  public List<Car> getCars() {
    return cars;
  }

  public void setCars(List<Car> cars) {
    this.cars = cars;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }
}
