package pl.edu.ug.astokwisz.projektap.controller.web;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.edu.ug.astokwisz.projektap.domain.User;
import pl.edu.ug.astokwisz.projektap.service.UserService;

import java.util.List;

@Controller
public class WebUserController {

    private final UserService userService;

    public WebUserController(@Autowired UserService userService) { this.userService = userService; }

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
    public String addUser(@Valid User user, Errors errors) {
        if (errors.hasErrors()) {
            System.out.println(errors.getAllErrors());
            return "adduser";
        }
        System.out.println(errors);
        System.out.println(user);
        userService.addUser(user);
        return "redirect:/";
    }
}
