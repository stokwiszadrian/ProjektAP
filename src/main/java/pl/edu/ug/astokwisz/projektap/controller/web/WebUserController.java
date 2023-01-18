package pl.edu.ug.astokwisz.projektap.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.edu.ug.astokwisz.projektap.service.UserService;

public class WebUserController {

    private final UserService userService;

    public WebUserController(@Autowired UserService userService) { this.userService = userService; }

    @GetMapping("/")
    public String home(Model model) {
        return "home";
    }
}
