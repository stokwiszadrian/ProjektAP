package pl.edu.ug.astokwisz.projektap.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.edu.ug.astokwisz.projektap.domain.Privilege;
import pl.edu.ug.astokwisz.projektap.domain.Role;
import pl.edu.ug.astokwisz.projektap.domain.User;
import pl.edu.ug.astokwisz.projektap.repository.RoleRepository;
import pl.edu.ug.astokwisz.projektap.repository.UserRepository;

import java.util.*;

@Service("userDetailsService")
@Transactional
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        Optional<User> user = userRepository.findUserByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("No user found with username: " + username);
        }

        User foundUser = user.get();
        return new org.springframework.security.core.userdetails.User(
                foundUser.getUsername(), foundUser.getPassword(), true, true, true,
                true, getAuthorities(foundUser.getRoles()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(
            Collection<Role> roles) {

        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<String> getPrivileges(Collection<Role> roles) {

        List<String> privileges = new ArrayList<>();
        List<Privilege> collection = new ArrayList<>();
        for (Role role : roles) {
            privileges.add(role.getName());
            collection.addAll(role.getPrivileges());
        }
        for (Privilege item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
}