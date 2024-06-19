package org.example.exception;

public class InvalidRequestException extends ApplicationException{

    public InvalidRequestException(int code, String message) {
        super(code, message);
    }
}
