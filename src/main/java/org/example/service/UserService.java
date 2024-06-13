package org.example.service;

import org.example.dao.UserDao;
import org.example.model.dto.NewUser;
import org.example.model.entity.User;

import java.util.List;

public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void create(NewUser user) {
        if (user != null) {
            userDao.createUser(user);
        }
    }

    public User findById(int id){
        return userDao.readUser(id);
    }

    public List<User> findAllUsers() {
        return userDao.readAllUsers();
    }
}
