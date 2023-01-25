package pl.edu.ug.astokwisz.projektap.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class Address {
    private long id;
    @Size(min = 1, message = "Pole wymagane")
    @Pattern(regexp = "^\\p{L}[\\p{L}\s]+$", message = "Niepoprawne dane")
    private String country;
    @Size(min = 1, message = "Pole wymagane")
    @Pattern(regexp = "^\\p{L}[\\p{L}\s]+$", message = "Niepoprawne dane")
    private String city;
    @Size(min = 1, message = "Pole wymagane")
    @Pattern(regexp = "^[\\p{L}0-9][\\p{L}\s]+$", message = "Niepoprawne dane")
    private String streetName;
    @Pattern(regexp = "^[\\p{L}0-9\s]+$", message = "Niepoprawne dane")
    private String streetNumber;

    @Pattern(regexp = "^[0-9]{1,6}$|", message = "Niepoprawne dane")
    private String apartmentNumber;
    @Pattern(regexp = "^[0-9]{2}-[0-9]{3}$", message = "Niepoprawne dane")
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
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

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }
}
