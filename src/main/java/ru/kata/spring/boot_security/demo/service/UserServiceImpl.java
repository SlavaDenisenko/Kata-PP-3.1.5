package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void saveUser(User user) {
        userDao.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public User findById(Long id) {
        return userDao.findById(id).orElse(null);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public void deleteById(Long id) {
        userDao.deleteById(id);
    }
}