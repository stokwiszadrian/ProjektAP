package pl.edu.ug.astokwisz.projektap.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;

@Entity
@Data
public class Privilege {
    private Long id;

    private String name;

//    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;

    public Privilege(String name) {
        this.name = name;
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