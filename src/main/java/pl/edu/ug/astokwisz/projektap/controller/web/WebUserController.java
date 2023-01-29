package pl.edu.ug.astokwisz.projektap.controller.web;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.edu.ug.astokwisz.projektap.domain.Role;
import pl.edu.ug.astokwisz.projektap.domain.User;
import pl.edu.ug.astokwisz.projektap.error.UserAlreadyExistsException;
import pl.edu.ug.astokwisz.projektap.service.RoleService;
import pl.edu.ug.astokwisz.projektap.service.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class WebUserController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final UserService userService;

    private final RoleService roleService;

    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
                isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }

    public WebUserController(@Autowired UserService userService, @Autowired RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
        List<User> userList = userService.getAllUsers();
        model.addAttribute("userList", userList);
        if (user.getUsername() != null) {
            Optional<User> userObjOpt = userService.getUserByUsername(user.getUsername());
            userObjOpt.ifPresent(value -> model.addAttribute("currentUserId", value.getId()));
        }
        return "home";
    }

    @GetMapping("/login")
    public String getLoginPage(@RequestParam(name = "continue", required = false) String c) {
        System.out.println(c);
        return "login";
    }

    @GetMapping("/adduser")
    public String addUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "adduser";
    }

    @GetMapping("/error")
    public String errorPage() {
        return "error";
    }

    @PostMapping("/adduser")
    public String addUser(Model model, @Valid User user, Errors errors) {
        if (errors.hasErrors()) {
            System.out.println(errors.getAllErrors());
            return "adduser";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Optional<Role> userRole = roleService.getRole("ROLE_USER");
        userRole.ifPresent(role -> user.setRoles(List.of(role)));
        try {
            userService.addUser(user);
            return "redirect:/";
        } catch (UserAlreadyExistsException userEx) {
            model.addAttribute("userExists", true);
            return "adduser";
        }
    }

    @GetMapping("/profile")
    public String getUserProfile(@RequestParam String id, Model model, @AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
        if (user.getUsername() != null) {
            String loggedUsername = user.getUsername();
            Optional<User> currentUserOpt = userService.getUserById(Long.valueOf(id));

            if (currentUserOpt.isPresent()) {
                User currentUser = currentUserOpt.get();

                if (Objects.equals(loggedUsername, currentUser.getUsername())) {
                    model.addAttribute("currentUser", currentUser);
                    return "profile";
                }
                model.addAttribute("errorMessage", "Brak dostępu do danego profilu użytkownika.");
                return "error";
            }
            model.addAttribute("errorMessage", "Użytkownik o podanym ID nie istnieje.");
            return "error";
        }
        model.addAttribute("errorMessage", "Brak dostępu do danego profilu użytkownika.");
        return "error";
    }
}
