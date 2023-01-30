package pl.edu.ug.astokwisz.projektap.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Entity
@Getter
@Setter
public class Privilege {
    private Long id;

    private String name;

//    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;

    public Privilege(String name) {
        this.name = name;
    }

    public Privilege() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return this.id;
    }

    @ManyToMany(mappedBy = "privileges")
    public Collection<Role> getRoles() {
        return this.roles;
    }
}