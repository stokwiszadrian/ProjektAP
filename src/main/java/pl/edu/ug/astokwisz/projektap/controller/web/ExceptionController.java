package pl.edu.ug.astokwisz.projektap.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.edu.ug.astokwisz.projektap.domain.User;
import pl.edu.ug.astokwisz.projektap.service.RoleService;
import pl.edu.ug.astokwisz.projektap.service.UserService;

import java.util.Optional;

@Controller
public class ExceptionController implements ErrorController {

    private final UserService userService;

    private final RoleService roleService;

    private void addUserAttribute(org.springframework.security.core.userdetails.User user, Model model) {
        if (user != null && user.getUsername() != null) {
            Optional<User> userObjOpt = userService.getUserByUsername(user.getUsername());
            userObjOpt.ifPresent(value -> model.addAttribute("currentUser", value));
        }
    }

    public ExceptionController(@Autowired UserService userService, @Autowired RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @RequestMapping("/error")
    public String handleError(Model model, @AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
        addUserAttribute(user, model);
        return "error";
    }

}
