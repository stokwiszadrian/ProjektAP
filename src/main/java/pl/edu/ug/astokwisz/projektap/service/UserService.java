package pl.edu.ug.astokwisz.projektap.service;

import pl.edu.ug.astokwisz.projektap.repository.AddressRepository;
import pl.edu.ug.astokwisz.projektap.repository.UserRepository;

public class UserService {
    final UserRepository userRepository;
    final AddressRepository addressRepository;

    public UserService(UserRepository userRepository, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository
    }

    public User addUser(User user) { return userRepository.save(user) }

    public List<User> getAllUsers() { return userRepository.findAll() }

    public void testing() {

    }
}
