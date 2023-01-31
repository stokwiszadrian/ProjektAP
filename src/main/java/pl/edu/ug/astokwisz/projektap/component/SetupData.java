package pl.edu.ug.astokwisz.projektap.component;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.edu.ug.astokwisz.projektap.domain.*;
import pl.edu.ug.astokwisz.projektap.repository.*;

import java.time.LocalDate;
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
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemTypeRepository itemTypeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;
        Privilege readPrivilege
                = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege
                = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

        List<Privilege> adminPrivileges = Arrays.asList(
                readPrivilege, writePrivilege);
        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_USER", List.of(readPrivilege));

        String encodedPass = passwordEncoder.encode("Password123!");
        User user = new User(
                "admin",
                encodedPass,
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

        User defaultUser = new User(
                "user123",
                encodedPass,
                "Adam",
                "Kowalski",
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
        Optional<Role> adminRoleOpt = roleRepository.findByName("ROLE_ADMIN");
        if (adminRoleOpt.isPresent()) {
            Role adminRole = adminRoleOpt.get();
            Optional<Role> userRoleOpt = roleRepository.findByName("ROLE_USER");
            if (userRoleOpt.isPresent()) {
                Role userRole = userRoleOpt.get();
                defaultUser.setRoles(List.of(userRole));
                user.setRoles(List.of(userRole, adminRole));
            }
        }
        userRepository.save(defaultUser);
        userRepository.save(user);

        ItemType type1 = new ItemType("Narty");
        ItemType type2 = new ItemType("Buty narciarskie");
        ItemType type3 = new ItemType("Kaski narciarskie");

        itemTypeRepository.saveAll(List.of(type1, type2, type3));

        Item nartySalomon = new Item(
              type1,
              "Narty Salomon X-Max X12",
                120.00F
        );

        Item butySalomon = new Item(
                type2,
                "Buty narciarskie Salomon S-Pro 100 20/21",
                120.00F,
                LocalDate.of(2022, 2, 11),
                LocalDate.of(2023, 2, 18),
                defaultUser

        );

        itemRepository.saveAll(List.of(nartySalomon, butySalomon));

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