package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UsersController {
    private final UserService userService;
    private final RoleDao roleDao;

    @Autowired
    public UsersController(UserService userService, RoleDao roleDao) {
        this.userService = userService;
        this.roleDao = roleDao;
    }

    @GetMapping(value = "/principal")
    public ResponseEntity<User> getCurrentUser(@AuthenticationPrincipal UserDetails user) {
        User currentUser = userService.findByUsername(user.getUsername());
        return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }

    @GetMapping(value = "/authorities")
    public ResponseEntity<List<Role>> getAuthorities() {
        List<Role> roles = roleDao.findAll();
        if (roles.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }
}
