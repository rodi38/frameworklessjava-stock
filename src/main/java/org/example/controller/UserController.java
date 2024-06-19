package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpHandler;
import org.example.model.dto.NewUser;
import org.example.model.dto.UserDto;
import org.example.model.entity.User;
import org.example.service.UserService;

import java.io.OutputStream;
import java.util.List;

public class UserController {

    private final UserService userService;
    private final ObjectMapper objectMapper;

    public UserController(UserService userService) {
        this.userService = userService;
        this.objectMapper = new ObjectMapper();
    }

    public HttpHandler create() {
        return exchange -> {
            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                userService.create(objectMapper.readValue(exchange.getRequestBody(), NewUser.class));
                String response = "User created successfully!";

                exchange.sendResponseHeaders(201, response.getBytes().length);

                OutputStream outputStream = exchange.getResponseBody();

                outputStream.write(response.getBytes());
                outputStream.flush();

            } else {
                exchange.sendResponseHeaders(405, -1);
            }

            exchange.close();
        };
    }

    public HttpHandler findAll() {


        return exchange -> {
            if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
                try {
                    List<UserDto> users = userService.findAll();

                    String response = objectMapper.writeValueAsString(users);
                    exchange.sendResponseHeaders(201, response.getBytes().length);
                    OutputStream outputStream = exchange.getResponseBody();

                    outputStream.write(response.getBytes());
                    outputStream.flush();

                } catch (Exception e) {

                }

            } else {
                exchange.sendResponseHeaders(405, -1);
            }
            exchange.close();
        };
    }

}
