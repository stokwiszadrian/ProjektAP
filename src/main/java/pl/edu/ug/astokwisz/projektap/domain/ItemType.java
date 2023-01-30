package pl.edu.ug.astokwisz.projektap.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ItemType {
    private long id;
    private String typeName;

    public ItemType() {
    }

    public ItemType(String typeName) {
        this.typeName = typeName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }


    public String toString() {
        return this.typeName;
    }
}
