package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpHandler;
import org.example.model.dto.NewUser;
import org.example.model.entity.User;
import org.example.service.UserService;

import java.io.OutputStream;

public class UserController {

    private final UserService userService;
    private final ObjectMapper objectMapper;

    public UserController(UserService userService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.objectMapper = objectMapper;
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

}
