package pl.edu.ug.astokwisz.projektap.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.Collection;

@Entity
@Data
public class Role {
    private Long id;

    private String name;
    private Collection<User> users;

//    @ManyToMany
//    @JoinTable(
//            name = "roles_privileges",
//            joinColumns = @JoinColumn(
//                    name = "role_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(
//                    name = "privilege_id", referencedColumnName = "id"))
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

    @ManyToMany(mappedBy = "roles")
    public Collection<User> getUsers() {
        return this.users;
    }

    @ManyToMany
    public Collection<Privilege> getPrivileges() {
        return this.privileges;
    }
}