package pl.edu.ug.astokwisz.projektap.controller.web;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.edu.ug.astokwisz.projektap.domain.Role;
import pl.edu.ug.astokwisz.projektap.domain.User;
import pl.edu.ug.astokwisz.projektap.error.UserAlreadyExistsException;
import pl.edu.ug.astokwisz.projektap.service.RoleService;
import pl.edu.ug.astokwisz.projektap.service.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class WebUserController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final UserService userService;

    private final RoleService roleService;

    public WebUserController(@Autowired UserService userService, @Autowired RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<User> userList = userService.getAllUsers();
        model.addAttribute("userList", userList);
        return "home";
    }

    @GetMapping("/adduser")
    public String addUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "adduser";
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
}
