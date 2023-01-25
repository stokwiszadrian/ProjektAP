package pl.ug.jneumann.app.springbootjpa.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Person {

  private long id;
  private String firstname;

  private List<Book> books;

  public Person() {
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  @ManyToMany()
  public List<Book> getBooks() {
    return books;
  }

  public void setBooks(List<Book> books) {
    this.books = books;
  }
}
