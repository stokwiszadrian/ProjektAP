package pl.edu.ug.astokwisz.projektap.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import pl.edu.ug.astokwisz.projektap.validator.UserEditChecks;

@Entity
@Getter
@Setter
public class Address {
    private long id;
    @Size(min = 1, message = "Pole wymagane", groups = UserEditChecks.class)
    @Pattern(regexp = "^\\p{L}[\\p{L}\s]+$", message = "Niepoprawne dane", groups = UserEditChecks.class)
    private String country;
    @Size(min = 1, message = "Pole wymagane", groups = UserEditChecks.class)
    @Pattern(regexp = "^\\p{L}[\\p{L}\s]+$", message = "Niepoprawne dane", groups = UserEditChecks.class)
    private String city;
    @Size(min = 1, message = "Pole wymagane", groups = UserEditChecks.class)
    @Pattern(regexp = "^[\\p{L}0-9][\\p{L}\s]+$", message = "Niepoprawne dane", groups = UserEditChecks.class)
    private String streetName;
    @Size(min = 1, message = "Pole wymagane", groups = UserEditChecks.class)
    @Pattern(regexp = "^[\\p{L}0-9\s]+$", message = "Niepoprawne dane", groups = UserEditChecks.class)
    private String streetNumber;

    @Pattern(regexp = "^[0-9]{1,6}$|", message = "Niepoprawne dane", groups = UserEditChecks.class)
    private String apartmentNumber;
    @Size(min = 1, message = "Pole wymagane", groups = UserEditChecks.class)
    @Pattern(regexp = "^[0-9]{2}-[0-9]{3}$", message = "Niepoprawne dane", groups = UserEditChecks.class)
    private String postalCode;

    public Address(String country, String city, String streetName, String streetNumber, String apartmentNumber, String postalCode) {
        this.country = country;
        this.city = city;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.apartmentNumber = apartmentNumber;
        this.postalCode = postalCode;
    }

    public Address() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", streetName='" + streetName + '\'' +
                ", streetNumber=" + streetNumber +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }

}
