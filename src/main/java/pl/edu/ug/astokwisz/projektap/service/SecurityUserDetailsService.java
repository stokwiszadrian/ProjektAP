//package pl.edu.ug.astokwisz.projektap.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import pl.edu.ug.astokwisz.projektap.domain.User;
//import pl.edu.ug.astokwisz.projektap.repository.UserRepository;
//
//import java.util.List;
//
//@Service
//public class SecurityUserDetailsService implements UserDetailsService {
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return userRepository.findUserByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not present"));
//    }
//
//}
//
