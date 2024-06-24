package org.example.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.BasicAuthenticator;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import org.example.config.Configuration;
import org.example.controller.UserController;


import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;



public class HttpServerConfig {

    public void startServer() throws IOException {

        UserController userController = new UserController(Configuration.getObjectMapper(),
                Configuration.getGlobalExceptionHandler(),
                Configuration.getUserService());


        int serverPort = 8000;
        HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);


        server.createContext("/api/users", userController::handle);
        server.createContext("/api/users/create", userController::handle);


        HttpContext context = server.createContext("/", (exchange -> {
            String response = "Hello, the API is working well!";

            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream output = exchange.getResponseBody();
            output.write(response.getBytes());
            output.flush();
            exchange.close();

        }));

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
