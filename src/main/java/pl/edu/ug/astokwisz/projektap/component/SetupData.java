package pl.edu.ug.astokwisz.projektap.component;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.edu.ug.astokwisz.projektap.domain.Address;
import pl.edu.ug.astokwisz.projektap.domain.Privilege;
import pl.edu.ug.astokwisz.projektap.domain.Role;
import pl.edu.ug.astokwisz.projektap.domain.User;
import pl.edu.ug.astokwisz.projektap.repository.PrivilegeRepository;
import pl.edu.ug.astokwisz.projektap.repository.RoleRepository;
import pl.edu.ug.astokwisz.projektap.repository.UserRepository;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
public class SetupData implements
        ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Privilege readPrivilege
                = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege
                = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

        List<Privilege> adminPrivileges = Arrays.asList(
                readPrivilege, writePrivilege);
        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_USER", List.of(readPrivilege));

        User user = new User(
                "admin",
                passwordEncoder.encode("password"),
                "Jan",
                "Nowak",
                new Address(
                        "Polska",
                        "Tczew",
                        "3 Maja",
                        "13",
                        "12",
                        "12-345"
                        ),
                "999999999",
                "00322000078");
        user.setFirstname("Test");
        user.setLastname("Test");
        user.setPassword(passwordEncoder.encode("test"));
        Optional<Role> adminRole = roleRepository.findByName("ROLE_ADMIN");
        adminRole.ifPresent(role -> user.setRoles(List.of(role)));
        userRepository.save(user);

        alreadySetup = true;
    }

    @Transactional
    Privilege createPrivilegeIfNotFound(String name) {

        Optional<Privilege> privilege = privilegeRepository.findByName(name);
        if (privilege.isEmpty()) {
            Privilege newPrivilege = new Privilege(name);
            privilegeRepository.save(newPrivilege);
            return newPrivilege;
        }
        return privilege.get();
    }

    @Transactional
    void createRoleIfNotFound(
            String name, Collection<Privilege> privileges) {

        Optional<Role> role = roleRepository.findByName(name);
        if (role.isEmpty()) {
            Role newRole = new Role(name);
            newRole.setPrivileges(privileges);
            roleRepository.save(newRole);
        }
//        role.get();
    }
}