package pl.edu.ug.astokwisz.projektap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import pl.edu.ug.astokwisz.projektap.domain.Item;
import pl.edu.ug.astokwisz.projektap.domain.ItemFilterForm;
import pl.edu.ug.astokwisz.projektap.domain.ItemType;
import pl.edu.ug.astokwisz.projektap.domain.User;
import pl.edu.ug.astokwisz.projektap.repository.ItemRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(@Autowired ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public void deleteById(Long id) { itemRepository.deleteById(id); }
    public Optional<Item> getItemById(Long id) { return itemRepository.findById(id); }

    public List<Item> getAllItems() { return (List<Item>) itemRepository.findAll(); }

    public Item updateItem(Item item) { return itemRepository.save(item); }

    public List<Item> getItemsByUser(User user) { return itemRepository.findByReservedBy(user); }

    public List<Item> getItemsByItemType(ItemType itemType) { return itemRepository.findByItemtype(itemType); }

    public List<Item> getFilteredItems(ItemFilterForm itemFilter) {
        return itemRepository.findAll(hasItemtype(itemFilter.getItemtype()));
    }

    static Specification<Item> hasItemtype(ItemType itemType) {
        if (itemType != null) {
            return (item, cq, cb) -> cb.equal(item.get("itemtype"), itemType);
        }
        return (item, cq, cb) -> cb.isNotNull(item.get("id"));
    }

}
