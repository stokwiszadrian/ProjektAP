package pl.edu.ug.astokwisz.projektap.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Entity
@Getter
@Setter
public class Role {
    private Long id;

    private String name;
    private Collection<User> users;

    private Collection<Privilege> privileges;

    public Role() {

    }

    public Role(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return this.id;
    }

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    public Collection<User> getUsers() {
        return this.users;
    }

    @ManyToMany
    public Collection<Privilege> getPrivileges() {
        return this.privileges;
    }
}