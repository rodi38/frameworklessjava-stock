package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import org.example.exception.Exceptions;
import org.example.exception.GlobalExceptionHandler;
import org.example.model.dto.NewUser;
import org.example.model.dto.ResponseEntity;
import org.example.model.dto.UserDto;
import org.example.server.Handler;
import org.example.service.UserService;
import org.example.util.Constants;
import org.example.util.StatusCode;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;


public class UserController extends Handler {

    private final UserService userService;

    public UserController(ObjectMapper objectMapper, GlobalExceptionHandler globalExceptionHandler, UserService userService) {
        super(objectMapper, globalExceptionHandler);
        this.userService = userService;
    }
    @Override
    protected void execute(HttpExchange exchange) throws Exception {
        byte[] response;

        if ("GET".equalsIgnoreCase(exchange.getRequestMethod())){
            ResponseEntity<List<UserDto>> req = findAll();

            exchange.getResponseHeaders().putAll(req.getHeaders());
            exchange.sendResponseHeaders(req.getStatusCode().getCode(), 0);

            response = super.writeResponse(req.getBody());


        } else if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            ResponseEntity<UserDto> req = create(exchange.getRequestBody());

            exchange.getResponseHeaders().putAll(req.getHeaders());
            exchange.sendResponseHeaders(req.getStatusCode().getCode(), 0);
            response = super.writeResponse(req.getBody());

        } else if ("PUT".equalsIgnoreCase(exchange.getRequestMethod())) {

            //todo
            response = super.writeResponse(null);
        } else if ("DELETE".equalsIgnoreCase(exchange.getRequestMethod())) {
            //todo
            response = super.writeResponse(null);
        } else {
            throw Exceptions.methodNotAllowedException(String.format("Method %s not allowed.", exchange.getRequestMethod()));
        }

        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(response);
        outputStream.flush();
        outputStream.close();
    }





    public ResponseEntity<UserDto> create(InputStream requestBody) {
        NewUser userRequest = super.readRequest(requestBody, NewUser.class);

        UserDto response = userService.create(userRequest);

        return new ResponseEntity<>(response, getHeaders(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON), StatusCode.CREATED );

    }

    public ResponseEntity<List<UserDto>> findAll() {
        List<UserDto> response = userService.findAll();

        return new ResponseEntity<>(response, getHeaders(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON), StatusCode.OK );
    }







//    public HttpHandler create() {
//        return exchange -> {
//            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
//                userService.create(objectMapper.readValue(exchange.getRequestBody(), NewUser.class));
//                String response = "User created successfully!";
//
//                exchange.sendResponseHeaders(201, response.getBytes().length);
//
//                OutputStream outputStream = exchange.getResponseBody();
//
//                outputStream.write(response.getBytes());
//                outputStream.flush();
//
//            } else {
//                exchange.sendResponseHeaders(405, -1);
//            }
//
//            exchange.close();
//        };
//    }
//
//    public HttpHandler findAll() {
//
//
//        return exchange -> {
//            if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
//                try {
//                    List<UserDto> users = userService.findAll();
//
//                    String response = objectMapper.writeValueAsString(users);
//                    exchange.sendResponseHeaders(201, response.getBytes().length);
//                    OutputStream outputStream = exchange.getResponseBody();
//
//                    outputStream.write(response.getBytes());
//                    outputStream.flush();
//
//                } catch (Exception e) {
//
//                }
//
//            } else {
//                exchange.sendResponseHeaders(405, -1);
//            }
//            exchange.close();
//        };
//    }


}
