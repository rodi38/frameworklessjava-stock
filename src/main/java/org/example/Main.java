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


    }
}