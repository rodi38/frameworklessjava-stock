package org.example.server;

import com.sun.net.httpserver.BasicAuthenticator;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

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
        HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);
        HttpContext context = server.createContext("/api/hello", (exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                Map<String, List<String>> params = splitQuery(exchange.getRequestURI().getRawQuery());


                String name = params.getOrDefault("name", List.of("Anônimo")).stream().findFirst().orElse("Anônimo");

                String response = String.format("Hello, %s, works well!", name);
                exchange.sendResponseHeaders(200, response.getBytes().length);

                OutputStream output = exchange.getResponseBody();
                output.write(response.getBytes());

                output.flush();
            } else {
                exchange.sendResponseHeaders(405, -1);
            }

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

    public Map<String, List<String>> splitQuery(String query) {
        if (query == null || query.isEmpty()) {
            return Collections.emptyMap();
        }

        return Pattern.compile("&").splitAsStream(query)
                .map(s -> Arrays.copyOf(s.split("="), 2))
                .collect(Collectors.groupingBy(s -> decode(s[0]),
                        Collectors.mapping(s -> decode(s[1]), Collectors.toList())));
    }


    public String decode(final String encoded) {
        return encoded == null ? null : URLDecoder.decode(encoded, StandardCharsets.UTF_8);
    }

    public void getRoute(String requestMethod) {

    }
}
