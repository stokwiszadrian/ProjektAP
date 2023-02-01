package pl.edu.ug.astokwisz.projektap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.ug.astokwisz.projektap.domain.ItemType;
import pl.edu.ug.astokwisz.projektap.repository.ItemTypeRepository;

import java.util.List;

@Service
public class ItemTypeService {
    private final ItemTypeRepository itemTypeRepository;

    public ItemTypeService(@Autowired ItemTypeRepository itemTypeRepository) {
        this.itemTypeRepository = itemTypeRepository;
    }

    public List<ItemType> getAllItemTypes() {
        return (List<ItemType>) itemTypeRepository.findAll();
    }
}
