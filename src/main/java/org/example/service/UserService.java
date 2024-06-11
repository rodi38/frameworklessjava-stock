package org.example.service;

import org.example.dao.UserDao;
import org.example.model.User;

import java.util.List;

public class UserService {

    private final UserDao userDao;

    public UserService(String url, String username, String password) {
        this.userDao = new UserDao(url, username, password);
    }

    public void create(User user) {
        userDao.createUser(user);
    }

    public User findById(int id){
        return userDao.readUser(id);
    }

    public List<User> findAllUsers() {
        return userDao.readAllUsers();
    }
}
