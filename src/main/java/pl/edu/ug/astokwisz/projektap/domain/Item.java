package pl.edu.ug.astokwisz.projektap.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Item {

    private long id;
    private ItemType itemType;
    private String name;
    private float price;
    private LocalDate reservedFrom;
    private LocalDate reservedTo;
    private User reservedBy;


    public Item() {
    }

    public Item(ItemType itemType, String name, float price, LocalDate reservedFrom, LocalDate reservedTo, User reservedBy) {
        this.itemType = itemType;
        this.name = name;
        this.price = price;
        this.reservedFrom = reservedFrom;
        this.reservedTo = reservedTo;
        this.reservedBy = reservedBy;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @OneToOne
    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public LocalDate getReservedFrom() {
        return reservedFrom;
    }

    public void setReservedFrom(LocalDate reservedFrom) {
        this.reservedFrom = reservedFrom;
    }

    public LocalDate getReservedTo() {
        return reservedTo;
    }

    public void setReservedTo(LocalDate reservedTo) {
        this.reservedTo = reservedTo;
    }

    @ManyToOne
    public User getReservedBy() {
        return reservedBy;
    }

    public void setReservedBy(User reservedBy) {
        this.reservedBy = reservedBy;
    }
}
