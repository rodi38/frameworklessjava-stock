package org.example.server;

import com.sun.net.httpserver.BasicAuthenticator;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import org.example.controller.UserController;
import org.example.dao.UserDao;
import org.example.service.UserService;
import org.example.util.api.ApiUtils;
import org.example.util.propertie.PropertiesLoader;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;


public class HttpServerConfig {
    private final int serverPort = 8000;

    public void startServer() throws IOException {

        Properties properties = PropertiesLoader.loadProperties();

        UserDao userDao = new UserDao( properties.getProperty("db.url"),  properties.getProperty("db.username"),  properties.getProperty("db.password"));
        UserService userService = new UserService(userDao);
        UserController userController = new UserController(userService);


        HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);
        HttpContext context = server.createContext("/", (exchange -> {
            String response = "Hello, the API is working well!";

            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream output = exchange.getResponseBody();
            output.write(response.getBytes());
            output.flush();
            exchange.close();

        }));

        server.createContext("/api/users", userController.findAll());
        server.createContext("/api/users/create", userController.create());

        context.setAuthenticator(new BasicAuthenticator("myrealm") {
            @Override
            public boolean checkCredentials(String username, String password) {
                return username.equals("admin") && password.equals("admin");
            }
        });

        server.setExecutor(null);
        server.start();
    }

}
