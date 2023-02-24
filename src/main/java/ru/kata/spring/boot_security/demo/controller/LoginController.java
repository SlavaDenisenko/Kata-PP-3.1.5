package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
public class LoginController {
    private final UserService userService;
    private final RoleDao roleDao;

    public LoginController(UserService userService, RoleDao roleDao) {
        this.userService = userService;
        this.roleDao = roleDao;
    }

    @GetMapping(value = "/admin")
    public String mainPage(ModelMap modelMap) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.addAttribute("user", userService.findByUsername(username));
        modelMap.addAttribute("roles", roleDao.findAll());
        return "admin";
    }

    @GetMapping(value = "/user")
    public String userPage(ModelMap modelMap) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.addAttribute("user", userService.findByUsername(username));
        return "user";
    }
}