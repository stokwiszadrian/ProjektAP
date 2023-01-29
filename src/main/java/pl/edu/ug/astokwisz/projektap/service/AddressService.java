package pl.edu.ug.astokwisz.projektap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.ug.astokwisz.projektap.domain.Address;
import pl.edu.ug.astokwisz.projektap.repository.AddressRepository;

import java.util.Optional;

@Service
public class AddressService {
    private final AddressRepository addressRepository;

    public AddressService(@Autowired AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public void deleteAddressById(Long id) { addressRepository.deleteById(id); }
    public Optional<Address> getAddressById(Long id) { return addressRepository.findById(id); }
}
