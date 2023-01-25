package pl.edu.ug.astokwisz.projektap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.ug.astokwisz.projektap.domain.User;
import pl.edu.ug.astokwisz.projektap.service.UserService;

import java.util.List;


@RestController
public class UserController {
    private final UserService userService;

    public UserController(@Autowired UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/user")
    User addUser(@RequestBody User user) { return userService.addUser(user); }

    @GetMapping("/api/user")
    List<User> getAll() { return userService.getAllUsers(); }

    @GetMapping
    public String home() {
        return "Home";
    }

    @GetMapping("/admin")
    public String admin() {
        return "Admin";
    }
}
