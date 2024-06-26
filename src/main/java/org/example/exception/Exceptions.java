package org.example.exception;

import org.example.util.StatusCode;

public class Exceptions {
    public static InvalidRequestException invalidRequestException(Throwable ex) {
        return new InvalidRequestException(400, ex.getMessage());
    }
    public static MethodNotAllowedException methodNotAllowedException(String message) {
        return new MethodNotAllowedException(StatusCode.METHOD_NOT_ALLOWED.getCode(), message);
    }
    public static ResourceNotFoundException resourceNotFoundException(String message) {
        return new ResourceNotFoundException(StatusCode.NOT_FOUND.getCode(), message);
    }

}
