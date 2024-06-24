package org.example.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.example.exception.Exceptions;
import org.example.exception.GlobalExceptionHandler;

import java.io.IOException;
import java.io.InputStream;

public abstract class Handler {


    private final ObjectMapper objectMapper;
    private final GlobalExceptionHandler globalExceptionHandler;


    public Handler(ObjectMapper objectMapper, GlobalExceptionHandler globalExceptionHandler) {
        this.objectMapper = objectMapper;
        this.globalExceptionHandler = globalExceptionHandler;
    }

    public void handle(HttpExchange exchange) throws IOException {
        try {
            execute(exchange);
        } catch (Throwable e) {
            globalExceptionHandler.handle(e, exchange);
        }
    }
    protected abstract void execute(HttpExchange exchange) throws Exception;


    protected <T> T readRequest(InputStream is, Class<T> type) {
        try {
            return objectMapper.readValue(is, type);
        } catch (Exception e) {
            throw Exceptions.invalidRequestException(e);
        }
    }

    protected <T> byte[] writeResponse(T response) {
        try {
            return objectMapper.writeValueAsBytes(response);
        } catch (Exception e) {
            throw Exceptions.invalidRequestException(e);
        }
    }

    protected static Headers getHeaders(String key, String value) {
        Headers headers = new Headers();
        headers.set(key, value);
        return headers;
    }

}
