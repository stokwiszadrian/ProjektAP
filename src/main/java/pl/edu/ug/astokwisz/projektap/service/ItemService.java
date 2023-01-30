package pl.edu.ug.astokwisz.projektap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.ug.astokwisz.projektap.domain.Item;
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
}
