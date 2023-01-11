package pl.ug.jneumann.app.springbootjpa;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.ug.jneumann.app.springbootjpa.service.CarService;

@SpringBootApplication
public class SpringbootjpaApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringbootjpaApplication.class, args);
  }

  @Bean
  public CommandLineRunner setUpApp(@Autowired CarService carService) {
    return (args) -> {

      carService.learning();

    };
  }

}
