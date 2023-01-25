//package pl.edu.ug.astokwisz.projektap.domain;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.HashSet;
//import java.util.Set;
//
//public class CustomUserDetails implements UserDetails {
//    private  User user;
//
//    public CustomUserDetails(final User _user) {
//        this.user = _user;
//    }
//
//    public CustomUserDetails() {
//    }
//
//    @Override
//    public Collection<GrantedAuthority> getAuthorities() {
//        final Set<GrantedAuthority> _grntdAuths = new HashSet<GrantedAuthority>();
//
//        GrantedAuthority g = new SimpleGrantedAuthority()
//
//        List<UserRole> _roles = null;
//
//        if (user!=null) {
//            _roles = user.getRoles();
//        }
//
//        if (_roles!=null) {
//            for (UserRole _role : _roles) {
//                _grntdAuths.add(new GrantedAuthorityImpl(_role.getRole()));
//            }
//        }
//
//        return _grntdAuths;
//    }
//
//    @Override
//    public String getPassword() {
//        return user.getPassword();
//    }
//    @Override
//    public String getUsername() {
//        if (this.user == null) {
//            return null;
//        }
//        return this.user.getUser_name();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return this.user.isAccountNonExpired();
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return this.user.isAccountNonLocked();
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return this.user.isCredentialsNonExpired();
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return this.user.isEnabled();
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    @Override
//    public String toString() {
//        return "CustomUserDetails [user=" + user + "]";
//    }
//}