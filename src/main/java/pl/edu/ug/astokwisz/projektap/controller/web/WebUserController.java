package pl.edu.ug.astokwisz.projektap.controller.web;

import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.groups.Default;
import org.hibernate.validator.internal.engine.groups.DefaultValidationOrder;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.edu.ug.astokwisz.projektap.domain.Item;
import pl.edu.ug.astokwisz.projektap.domain.ItemType;
import pl.edu.ug.astokwisz.projektap.domain.Role;
import pl.edu.ug.astokwisz.projektap.domain.User;
import pl.edu.ug.astokwisz.projektap.error.UserAlreadyExistsException;
import pl.edu.ug.astokwisz.projektap.service.*;
import pl.edu.ug.astokwisz.projektap.validator.UserEditChecks;

import javax.swing.text.html.Option;
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

    private final AddressService addressService;

    private final ItemService itemService;

    private final ItemTypeService itemTypeService;

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

    public WebUserController(
            @Autowired UserService userService,
            @Autowired RoleService roleService,
            @Autowired AddressService addressService,
            @Autowired ItemService itemService,
            @Autowired ItemTypeService itemTypeService) {
        this.userService = userService;
        this.roleService = roleService;
        this.addressService = addressService;
        this.itemService = itemService;
        this.itemTypeService = itemTypeService;
    }

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
        List<Item> itemList = itemService.getAllItems();
        model.addAttribute("itemList", itemList);
        addUserAttribute(user, model);
        return "itemlist";
    }

    @GetMapping("/bookitem")
    public String bookItemForm(@RequestParam String id, Model model, @AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
        addUserAttribute(user, model);
        Optional<Item> currentItemOpt = itemService.getItemById(Long.valueOf(id));
        if (currentItemOpt.isPresent()) {
            Item currentItem = currentItemOpt.get();
            model.addAttribute("bookedItem", currentItem);
            model.addAttribute("action", "/bookitem?id=" + currentItem.getId());
            return "bookitem";
        }
        model.addAttribute("errorMessage", "Dany przedmiot nie istnieje.");
        return "error";


    }

    @PostMapping("/bookitem")
    public String bookItemForm(@RequestParam String id, Model model, @ModelAttribute("bookedItem") Item item, @AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
        addUserAttribute(user, model);
        if (item.getReservedFrom().isAfter(item.getReservedTo())) {
            model.addAttribute("errorMessage", "Podano zły zakres.");
            return "error";
        }
        Optional<Item> fillerItemOpt = itemService.getItemById(Long.valueOf(id));
        if (fillerItemOpt.isPresent()) {
            Item fillerItem = fillerItemOpt.get();
            item.setName(fillerItem.getName());
            item.setPrice(fillerItem.getPrice());
            item.setItemtype(fillerItem.getItemtype());
            Optional<User> currentUserOpt = userService.getUserByUsername(user.getUsername());
            currentUserOpt.ifPresent(item::setReservedBy);
            itemService.updateItem(item);
            return "redirect:/";
        }
        model.addAttribute("errorMessage", "Dany przedmiot nie istnieje.");
        return "error";


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
    public String addUser(Model model, @Validated({Default.class, UserEditChecks.class}) @ModelAttribute("userToAdd") User user, Errors errors, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authUser) {
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
    public String editUser(@RequestParam String id, Model model, @AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
        if (user != null && user.getUsername() != null) {
            String loggedUsername = user.getUsername();
            Optional<User> currentUserOpt = userService.getUserById(Long.valueOf(id));

            if (currentUserOpt.isPresent()) {
                User currentUser = currentUserOpt.get();

                if (Objects.equals(loggedUsername, currentUser.getUsername())) {
                    model.addAttribute("userToAdd", currentUser);
                    model.addAttribute("currentUser", currentUser);
                    model.addAttribute("isEditForm", true);
                    model.addAttribute("action", "/edituser?id=" + currentUser.getId());
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

    @PostMapping("/edituser")
    public String editUser(Model model, @Validated(UserEditChecks.class) @ModelAttribute("userToAdd") User user, Errors errors, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authUser, @RequestParam String id) {
        addUserAttribute(authUser, model);
        Optional<User> fillerUserOpt = userService.getUserById(Long.valueOf(id));
        if (fillerUserOpt.isPresent()) {
            User fillerUser = fillerUserOpt.get();
            user.setUsername(fillerUser.getUsername());
            user.setPassword(fillerUser.getPassword());
            user.setReservedItems(fillerUser.getReservedItems());
            user.setRoles(fillerUser.getRoles());
            model.addAttribute("action", "/edituser?id=" + id);
            model.addAttribute("isEditForm", true);
            if (errors.hasErrors()) {
                System.out.println("ERRORS: " + errors);
                return "adduser";
            }
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
            try {
//                System.out.println(user);
                userService.updateUser(user);
                addressService.deleteAddressById(fillerUser.getAddress().getId());
                return "redirect:/profile?id=" + id;
            } catch (UserAlreadyExistsException userEx) {
                model.addAttribute("userExists", true);
                return "adduser";
            }
        }
        return "redirect:/profile?id=" + id;
    }

    @PostMapping("/cancelreservation")
    public String cancelReservation(Model model, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authUser, @RequestParam String id) {
        addUserAttribute(authUser, model);
        Optional<Item> itemOpt = itemService.getItemById(Long.valueOf(id));
        if (itemOpt.isPresent()) {
            Item item = itemOpt.get();
            item.setReservedBy(null);
            item.setReservedFrom(null);
            item.setReservedTo(null);
            itemService.updateItem(item);
            Optional<User> curUserOpt = userService.getUserByUsername(authUser.getUsername());
            if (curUserOpt.isPresent()) {
                User curUser = curUserOpt.get();
                return "redirect:/profile?id=" + curUser.getId();
            }
        }
        model.addAttribute("errorMessage", "Nie udało się anulować rezerwacji - dany przedmiot nie istnieje.");
        return "error";
    }

    @GetMapping("/adminpage")
    public String getAdminPage(Model model, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authUser) {
        addUserAttribute(authUser, model);
        return "adminpage";
    }

    @GetMapping("/adminpage/items")
    public String adminPageItems(Model model, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authUser) {
        addUserAttribute(authUser, model);
        List<Item> itemList = itemService.getAllItems();
        model.addAttribute("itemList", itemList);
        return "adminpage_items";
    }

    @PostMapping("/adminpage/deleteitem")
    public String deleteItem(Model model, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authUser, @RequestParam String id) {
        addUserAttribute(authUser, model);
        itemService.deleteById(Long.valueOf(id));
        return "redirect:/adminpage/items";
    }

    @GetMapping("/adminpage/additem")
    public String addItem(Model model, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authUser) {
        addUserAttribute(authUser, model);
        Item itemToAdd = new Item();
        model.addAttribute("itemToAdd", itemToAdd);
        List<ItemType> itemTypes = itemTypeService.getAllItemTypes();
        model.addAttribute("itemTypes", itemTypes);
        model.addAttribute("action", "/adminpage/additem");
        return "adminpage_itemform";
    }

    @GetMapping("/adminpage/edititem")
    public String editItem(Model model, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authUser, @RequestParam String id) {
        addUserAttribute(authUser, model);
        Optional<Item> itemToAddOpt = itemService.getItemById(Long.valueOf(id));
        if (itemToAddOpt.isPresent()) {
            Item itemToAdd = itemToAddOpt.get();
            model.addAttribute("itemToAdd", itemToAdd);
            List<ItemType> itemTypes = itemTypeService.getAllItemTypes();
            model.addAttribute("itemTypes", itemTypes);
            model.addAttribute("action", "/adminpage/additem");
            return "adminpage_itemform";
        }
        model.addAttribute("errorMessage", "Przedmiot o podanym ID nie istnieje.");
        return "error";
    }

    @PostMapping("/adminpage/additem")
    public String addItem(Model model, @Valid @ModelAttribute("itemToAdd") Item item, Errors errors, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authUser) {
        addUserAttribute(authUser, model);
        System.out.println(item);
        if (errors.hasErrors()) {
            model.addAttribute("action", "/adminpage/additem");
            List<ItemType> itemTypes = itemTypeService.getAllItemTypes();
            model.addAttribute("itemTypes", itemTypes);
            return "adminpage_itemform";
        }
//        item.setId(Long.valueOf());
        itemService.updateItem(item);
        return "redirect:/adminpage/items";

    }

    @GetMapping("/adminpage/users")
    public String adminPageUsers(Model model, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authUser) {
        addUserAttribute(authUser, model);
        List<User> userList = userService.getAllUsers();
        model.addAttribute("userList", userList);
        return "adminpage_users";
    }

    @PostMapping("/adminpage/deleteuser")
    public String deleteUser(Model model, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authUser, @RequestParam String id) {
        addUserAttribute(authUser, model);
        Optional<User> userToDeleteOpt = userService.getUserById(Long.valueOf(id));
        if (userToDeleteOpt.isPresent()) {
            User userToDelete = userToDeleteOpt.get();
            List<Item> userBookedItems = itemService.getItemsByUser(userToDelete);
            for (Item item : userBookedItems) {
                item.setReservedBy(null);
                item.setReservedFrom(null);
                item.setReservedTo(null);
                itemService.updateItem(item);
            }
            userService.deleteUserById(Long.valueOf(id));
            return "redirect:/adminpage/users";
        }
        model.addAttribute("errorMessage", "Użytkownik o podanym ID nie istnieje.");
        return "error";
    }



}
