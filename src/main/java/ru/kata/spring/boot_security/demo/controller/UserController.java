package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class UserController {
    private final UserService userService;
    private final RoleDao roleDao;

    public UserController(UserService userService, RoleDao roleDao) {
        this.userService = userService;
        this.roleDao = roleDao;
    }

    @GetMapping(value = "/user")
    public String userData(ModelMap modelMap) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.addAttribute("user", userService.findByUsername(username));
        return "index";
    }

    @GetMapping(value = "/admin")
    public String admin(ModelMap modelMap) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<User> users = userService.findAll();
        modelMap.addAttribute("users", users);
        modelMap.addAttribute("user", userService.findByUsername(username));
        return "admin";
    }

    @GetMapping(value = "/user-create")
    public String createUserForm(User user, ModelMap modelMap) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.addAttribute("user", userService.findByUsername(username));
        modelMap.addAttribute("users", user);
        modelMap.addAttribute("roles", roleDao.findAll());
        return "user-create";
    }

    @PostMapping(value = "/user-create")
    public String createUser(@RequestParam("role") String role, User user) {
        Set<Role> roles = new HashSet<>();
        Role roleUser = roleDao.findByName(role);
        roles.add(roleUser);
        user.setRoles(roles);
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping(value = "/user-delete/{id}")
    public String deleteUserForm(@PathVariable("id") Long id, ModelMap modelMap) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findById(id);
        modelMap.addAttribute("user", userService.findByUsername(username));
        modelMap.addAttribute("allUsers", userService.findAll());
        modelMap.addAttribute("roles", roleDao.findAll());
        modelMap.addAttribute("users", user);
        return "user-delete";
    }

    @PostMapping(value = "/user-delete")
    public String deleteUser(User user) {
        userService.deleteById(user.getId());
        return "redirect:/admin";
    }

    @GetMapping(value = "/user-update/{id}")
    public String updateUserForm(@PathVariable("id") Long id, ModelMap modelMap) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findById(id);
        modelMap.addAttribute("user", userService.findByUsername(username));
        modelMap.addAttribute("allUsers", userService.findAll());
        modelMap.addAttribute("roles", roleDao.findAll());
        modelMap.addAttribute("users", user);
        return "user-update";
    }

    @PostMapping(value = "/user-update")
    public String updateUser(@RequestParam("role") String role, User user) {
        Set<Role> roles = new HashSet<>();
        Role roleUser = roleDao.findByName(role);
        roles.add(roleUser);
        user.setRoles(roles);
        userService.saveUser(user);
        return "redirect:/admin";
    }
}