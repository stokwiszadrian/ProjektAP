package pl.edu.ug.astokwisz.projektap.domain;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.validator.constraints.UniqueElements;
import org.hibernate.validator.constraints.pl.PESEL;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.ug.astokwisz.projektap.annotation.PasswordValidation;
import pl.edu.ug.astokwisz.projektap.validator.UserEditChecks;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    private long id;
    @NotNull(message = "Pole wymagane")
    @Size(min = 4, max = 20, message = "Nazwa użytkownika musi mieć mieć od 4 do 20 znaków")
    @Column(unique = true)
    private String username;
    @PasswordValidation
    private String password;
    @Size(min = 1, message = "Pole wymagane", groups = UserEditChecks.class)
    @Pattern(regexp = "^\\p{L}[\\p{L}[:blank:]]+$", message = "Niepoprawne dane", groups = UserEditChecks.class)
    private String firstname;
    @Size(min = 1, message = "Pole wymagane", groups = UserEditChecks.class)
    @Pattern(regexp = "^\\p{L}[\\p{L}[:blank:]]+$", message = "Niepoprawne dane", groups = UserEditChecks.class)
    private String lastname;

    @Valid
    private Address address;
    @Pattern(regexp = "[0-9]{9}", message = "Podany numer telefonu jest niepoprawny", groups = UserEditChecks.class)
    private String phoneNumber;

    @PESEL(message = "Niepoprawny numer PESEL", groups = UserEditChecks.class)
    private String pesel;

    private Collection<Item> reservedItems;
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

    @ManyToMany(fetch = FetchType.EAGER)
    public Collection<Role> getRoles() {
        return this.roles;
    }

    @OneToOne(cascade = CascadeType.ALL)
    public Address getAddress() {
        return address;
    }

    @OneToMany(mappedBy="reservedBy", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    public Collection<Item> getReservedItems() { return reservedItems; }

}
