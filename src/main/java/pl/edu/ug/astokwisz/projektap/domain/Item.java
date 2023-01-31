package pl.edu.ug.astokwisz.projektap.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Item {

    private long id;
    
    @NotNull
    private ItemType itemtype;
    
    @NotNull
    @Size(min = 1, message = "Pole wymagane")
    private String name;
    
    @NotNull
//    @Digits(fraction = 2, message = "Cena powinna zawierać 2 miejsca po przecinku", integer = 6)
    @Min(value = 1, message = "Cena nie może być mniejsza od 1.")
    private float price;
    private LocalDate reservedFrom;
    private LocalDate reservedTo;
    private User reservedBy;


    public Item() {
    }

    public Item(ItemType itemtype, String name, float price, LocalDate reservedFrom, LocalDate reservedTo, User reservedBy) {
        this.itemtype = itemtype;
        this.name = name;
        this.price = price;
        this.reservedFrom = reservedFrom;
        this.reservedTo = reservedTo;
        this.reservedBy = reservedBy;
    }

    public Item(ItemType itemtype, String name, float price) {
        this.itemtype = itemtype;
        this.name = name;
        this.price = price;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    @OneToOne
    public ItemType getItemtype() {
        return itemtype;
    }

    @ManyToOne(cascade = CascadeType.REFRESH)
    public User getReservedBy() {
        return reservedBy;
    }

    public String minReservedString() {
        return LocalDate.now().toString();
    }

    public boolean available() {
        if (reservedTo == null) return true;
        else {
            LocalDate today = LocalDate.now();
            return today.isAfter(reservedTo);
        }
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", itemtype=" + itemtype +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", reservedFrom=" + reservedFrom +
                ", reservedTo=" + reservedTo +
                ", reservedBy=" + reservedBy +
                '}';
    }
}
