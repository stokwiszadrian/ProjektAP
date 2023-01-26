package pl.edu.ug.astokwisz.projektap.service;

import org.springframework.stereotype.Service;
import pl.edu.ug.astokwisz.projektap.domain.Role;
import pl.edu.ug.astokwisz.projektap.repository.RoleRepository;

import java.util.Optional;

@Service
public class RoleService {
    final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Optional<Role> getRole(String name) {
        return roleRepository.findByName(name);
    }
}
