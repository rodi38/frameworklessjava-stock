package org.example.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.model.dto.ErrorResponse;
import org.example.util.Constants;

import java.io.IOException;
import java.io.OutputStream;

public class GlobalExceptionHandler {

    private final ObjectMapper objectMapper;

    public GlobalExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    public void handle(Throwable throwable, HttpExchange exchange) throws IOException {
        try {
            throwable.printStackTrace();

            exchange.getRequestHeaders().set(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON);

            ErrorResponse res = getErrorResponse(throwable, exchange);
            OutputStream resBody = exchange.getResponseBody();
            resBody.write(objectMapper.writeValueAsBytes(res));
            resBody.flush();
            resBody.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ErrorResponse getErrorResponse(Throwable throwable, HttpExchange exchange) throws IOException {
        ErrorResponse errorResponse = new ErrorResponse();


        if (throwable instanceof InvalidRequestException) {

            InvalidRequestException exception = (InvalidRequestException) throwable;
            errorResponse.setCode(exception.getCode());
            errorResponse.setMessage(exception.getMessage());
            exchange.sendResponseHeaders(400, 0);

        } else if (throwable instanceof MethodNotAllowedException) {

            MethodNotAllowedException exception = (MethodNotAllowedException) throwable;
            errorResponse.setCode(exception.getCode());
            errorResponse.setMessage(exception.getMessage());
            exchange.sendResponseHeaders(404, 0);

        } else if (throwable instanceof ResourceNotFoundException) {

            ResourceNotFoundException exception = (ResourceNotFoundException) throwable;
            errorResponse.setCode(exception.getCode());
            errorResponse.setMessage(exception.getMessage());
            exchange.sendResponseHeaders(500, 0);

        } else {

            errorResponse.setCode(500);
            errorResponse.setMessage(throwable.getMessage());
            exchange.sendResponseHeaders(500, 0);

        }

        return errorResponse;
    }
}
