package pl.edu.ug.astokwisz.projektap.service;

import org.springframework.stereotype.Service;
import pl.edu.ug.astokwisz.projektap.domain.User;
import pl.edu.ug.astokwisz.projektap.repository.AddressRepository;
import pl.edu.ug.astokwisz.projektap.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    final UserRepository userRepository;
    final AddressRepository addressRepository;

    public UserService(UserRepository userRepository, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() { return (List<User>) userRepository.findAll(); }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllByFullname(String firstname, String lastname) {
        return userRepository.findByFirstnameAndLastname(firstname, lastname);
    }
}
