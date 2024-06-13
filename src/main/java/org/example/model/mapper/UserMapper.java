package org.example.model.mapper;

import org.example.model.dto.NewUser;
import org.example.model.dto.UserDto;
import org.example.model.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {



    public static User newUserToUser(NewUser newUser) {
        User user = new User();
        user.setName(newUser.getName());
        user.setEmail(newUser.getEmail());
        user.setPassword(newUser.getPassword());
        return user;
    }

    public static List<UserDto> userListToUserDtoList(List<User> users) {
        List<UserDto> userDtos = new ArrayList<>();

        for (User user : users) {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setName(user.getName());
            userDto.setEmail(user.getEmail());
            userDto.setPassword(user.getPassword());
            userDtos.add(userDto);
        }
        return userDtos;
    }

    public static UserDto userToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        return userDto;
    }




}
