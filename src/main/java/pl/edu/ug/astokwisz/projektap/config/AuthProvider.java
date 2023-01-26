//package pl.edu.ug.astokwisz.projektap.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import pl.edu.ug.astokwisz.projektap.domain.CustomUserDetailsService;
//import pl.edu.ug.astokwisz.projektap.repository.UserRepository;
//
//@Component
//public class AuthProvider implements AuthenticationProvider {
//    @Autowired
//    private CustomUserDetailsService userDetailsService;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//    @Autowired
//    private UserRepository userRepository;
//
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String username = authentication.getName();
//        authentication.setAuthenticated();
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return true;
//    }
//}
