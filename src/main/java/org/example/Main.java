package org.example;

import org.example.model.User;
import org.example.server.HttpServerConfig;
import org.example.service.UserService;
import org.example.util.PropertiesLoader;

import javax.imageio.IIOException;
import java.io.IOException;
import java.util.Properties;



public class Main {
    public static void main(String[] args) throws IOException {
        HttpServerConfig httpServerConfig = new HttpServerConfig();

        httpServerConfig.startServer();

        Properties properties = PropertiesLoader.loadProperties();


        String url = properties.getProperty("db.url");
        String username = properties.getProperty("db.username");
        String password = properties.getProperty("db.password");



        UserService userService = new UserService(url, username, password);

//
//        User user = new User();
//
//        user.setName("Rodrigo");
//        user.setEmail("Rocha");
//        user.setPassword("1233455");
//
//        userService.create(user);
        


    }
}