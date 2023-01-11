package pl.ug.jneumann.app.springbootjpa.domain;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.List;

@Entity
public class Book {

  private Long id;
  private String title;
  private int yop;

  private List<Person> author;

  public Book() {
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  @ManyToMany(mappedBy = "books")
  public List<Person> getAuthor() {
    return author;
  }

  public void setAuthor(List<Person> author) {
    this.author = author;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @NonNull
  public int getYop() {
    return yop;
  }

  public void setYop(int yop) {
    this.yop = yop;
  }
}
