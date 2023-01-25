package pl.edu.ug.astokwisz.projektap.domain;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.validator.constraints.pl.PESEL;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import pl.edu.ug.astokwisz.projektap.annotation.PasswordValidation;

import java.util.Collection;
import java.util.List;

@Entity
@Data
public class User {
    private long id;
    @NotNull(message = "Pole wymagane")
    @Size(min = 4, max = 20, message = "Nazwa użytkownika musi mieć mieć od 4 do 20 znaków")
    private String username;
    @PasswordValidation
    private String password;
    @Size(min = 1, message = "Pole wymagane")
    @Pattern(regexp = "^\\p{L}[\\p{L}[:blank:]]+$", message = "Niepoprawne dane")
    private String firstname;
    @Size(min = 1, message = "Pole wymagane")
    @Pattern(regexp = "^\\p{L}[\\p{L}[:blank:]]+$", message = "Niepoprawne dane")
    private String lastname;

    @Valid
    private Address address;
    @Pattern(regexp = "[0-9]{9}", message = "Podany numer telefonu jest niepoprawny")
    private String phoneNumber;

    @PESEL(message = "Niepoprawny numer PESEL")
    private String pesel;

    private List<Item> reservedItems;

//    @JoinTable(
//            name = "users_roles",
//            joinColumns = @JoinColumn(
//                    name = "user_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(
//                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    public User() {
    }

    public User(String username, String password, String firstname, String lastname, Address address, String phoneNumber, String pesel) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.pesel = pesel;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }
    @ManyToMany
    public Collection<Role> getRoles() {
        return this.roles;
    }

//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getFirstname() {
//        return firstname;
//    }
//
//    public void setFirstname(String firstname) {
//        this.firstname = firstname;
//    }
//
//    public String getLastname() {
//        return lastname;
//    }
//
//    public void setLastname(String lastname) {
//        this.lastname = lastname;
//    }

    @OneToOne(cascade = CascadeType.ALL)
    public Address getAddress() {
        return address;
    }

//    public void setAddress(Address address) {
//        this.address = address;
//    }
//
//    public String getPhoneNumber() {
//        return phoneNumber;
//    }
//
//    public void setPhoneNumber(String phoneNumber) {
//        this.phoneNumber = phoneNumber;
//    }
//
//    public String getPesel() {
//        return pesel;
//    }
//
//    public void setPesel(String pesel) {
//        this.pesel = pesel;
//    }

    @OneToMany(mappedBy="reservedBy")
    public Collection<Item> getReservedItems() { return reservedItems; }

//    public void setReservedItems(List<Item> reservedItems) { this.reservedItems = reservedItems; }
//
//    @Override
//    public String toString() {
//        return "User{" +
//                "id=" + id +
//                ", username='" + username + '\'' +
//                ", password='" + password + '\'' +
//                ", firstname='" + firstname + '\'' +
//                ", lastname='" + lastname + '\'' +
//                ", address=" + address +
//                ", phoneNumber='" + phoneNumber + '\'' +
//                ", pesel='" + pesel + '\'' +
//                ", reservedItems=" + reservedItems +
//                '}';
//    }
}
