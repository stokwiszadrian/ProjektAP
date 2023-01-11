package pl.edu.ug.astokwisz.projektap.domain;

import jakarta.persistence.*;
import org.springframework.core.annotation.MergedAnnotation;

@Entity
public class User {
    private long id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private Address address;
    private String phoneNumber;

    private String pesel;
    private boolean isAdmin;

    public User() {
    }

    public User(String username, String password, String firstname, String lastname, Address address, String phoneNumber, boolean isAdmin, String pesel) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.isAdmin = isAdmin;
        this.pesel = pesel;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @OneToOne
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }
}
