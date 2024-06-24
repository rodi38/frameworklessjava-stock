package org.example.service;

import org.example.dao.UserDao;
import org.example.model.dto.NewUser;
import org.example.model.dto.UserDto;
import org.example.model.entity.User;
import org.example.model.mapper.UserMapper;

import java.util.List;

public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserDto create(NewUser request) {
        User user = userDao.createUser(UserMapper.newUserToUser(request));
        System.out.println(user.getId());
        return UserMapper.userToUserDto(user);

    }

    public UserDto findById(int id) {
        return UserMapper.userToUserDto(userDao.readUser(id));
    }

    public List<UserDto> findAll() {
        return UserMapper.userListToUserDtoList(userDao.readAllUsers());
    }


}
