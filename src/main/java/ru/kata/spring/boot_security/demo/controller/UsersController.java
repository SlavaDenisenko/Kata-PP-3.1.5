package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@RestController
@RequestMapping(value = "/admin")
public class UsersController {
    private final UserService userService;
    private final RoleDao roleDao;

    public UsersController(UserService userService, RoleDao roleDao) {
        this.userService = userService;
        this.roleDao = roleDao;
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<User> getUser(@PathVariable(name = "id") Long id) {
        if (id == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        User user = this.userService.findById(id);
        if (user == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(value = "/add-new-user")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        if (user == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        this.userService.saveUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping(value = "")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        if (user == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        this.userService.saveUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<User> deleteUser(@PathVariable(name = "id") Long id) {
        User user = this.userService.findById(id);
        if (user == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        this.userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll();
        if (users.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "/authorities")
    public ResponseEntity<List<Role>> getAuthorities() {
        List<Role> roles = roleDao.findAll();
        if (roles.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }
}