package org.example.exception;

public class ResourceNotFoundException extends ApplicationException{
    public ResourceNotFoundException(int code, String message) {
        super(code, message);
    }
}
