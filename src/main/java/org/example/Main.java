package org.example;

import org.example.dao.UserDao;
import org.example.server.HttpServerConfig;
import org.example.service.UserService;
import org.example.util.propertie.PropertiesLoader;

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



        UserService userService = new UserService(new UserDao(url, username, password));



    }
}