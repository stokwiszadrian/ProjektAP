package pl.edu.ug.astokwisz.projektap.domain;

import lombok.Data;

@Data
public class ItemFilterForm {

    private ItemType itemtype;
    private Float minPrice;
    private Float maxPrice;
    private String orderBy = "nameAsc";

    public ItemFilterForm() {}

}
