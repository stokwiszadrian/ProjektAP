package pl.ug.jneumann.app.springbootjpa.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Car {

  private Long id;
  private String make;

  private int yop;

  public Car(String make, int yop) {
    this.make = make;
    this.yop = yop;
  }

  public Car() {
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getMake() {
    return make;
  }

  public void setMake(String make) {
    this.make = make;
  }

  public int getYop() {
    return yop;
  }

  public void setYop(int yop) {
    this.yop = yop;
  }

  @Override
  public String toString() {
    return "Car{" +
        "id=" + id +
        ", make='" + make + '\'' +
        ", yop=" + yop +
        '}';
  }
}
