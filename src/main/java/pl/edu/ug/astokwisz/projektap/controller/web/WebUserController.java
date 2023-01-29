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
import org.springframework.web.bind.annotation.ModelAttribute;
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

    private void addUserAttribute(org.springframework.security.core.userdetails.User user, Model model) {
        if (user != null && user.getUsername() != null) {
            Optional<User> userObjOpt = userService.getUserByUsername(user.getUsername());
            userObjOpt.ifPresent(value -> model.addAttribute("currentUser", value));
        }
    }

    public WebUserController(@Autowired UserService userService, @Autowired RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
        List<User> userList = userService.getAllUsers();
        model.addAttribute("userList", userList);
        addUserAttribute(user, model);
        return "home";
    }

    @GetMapping("/login")
    public String getLoginPage(@RequestParam(name = "continue", required = false) String c) {
        return "login";
    }

    @GetMapping("/adduser")
    public String addUser(Model model, @AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
        model.addAttribute("action", "adduser");
        addUserAttribute(user, model);
        model.addAttribute("userToAdd", new User());
        return "adduser";
    }

//    @GetMapping("/error")
//    public String errorPage(Model model, @AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {;
//        addUserAttribute(user, model);
//        return "error";
//    }

    @PostMapping("/adduser")
    public String addUser(Model model, @Valid @ModelAttribute("userToAdd") User user, Errors errors, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authUser) {
        addUserAttribute(authUser, model);
        model.addAttribute("action", "/adduser");
//        model.addAttribute("userToAdd", user);
        if (errors.hasErrors()) {
            System.out.println("ERRORS: " + errors);
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
        if (user != null && user.getUsername() != null) {
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

    @GetMapping("/edituser")
    public String editUserProfile(@RequestParam String id, Model model, @AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
        if (user != null && user.getUsername() != null) {
            String loggedUsername = user.getUsername();
            Optional<User> currentUserOpt = userService.getUserById(Long.valueOf(id));

            if (currentUserOpt.isPresent()) {
                User currentUser = currentUserOpt.get();

                if (Objects.equals(loggedUsername, currentUser.getUsername())) {
                    model.addAttribute("userToAdd", currentUser);
                    model.addAttribute("currentUser", currentUser);
                    return "adduser";
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
