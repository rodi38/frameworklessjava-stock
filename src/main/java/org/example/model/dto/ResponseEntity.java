package org.example.model.dto;

import com.sun.net.httpserver.Headers;
import org.example.util.StatusCode;

import java.util.Objects;

public class ResponseEntity<T> {

    private final T body;
    private final Headers headers;
    private final StatusCode statusCode;

    public ResponseEntity(T body, Headers headers, StatusCode statusCode) {
        this.body = body;
        this.headers = headers;
        this.statusCode = statusCode;
    }

    public T getBody() {
        return body;
    }

    public Headers getHeaders() {
        return headers;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResponseEntity)) return false;
        ResponseEntity<?> that = (ResponseEntity<?>) o;
        return Objects.equals(getBody(), that.getBody()) && Objects.equals(getHeaders(), that.getHeaders()) && getStatusCode() == that.getStatusCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBody(), getHeaders(), getStatusCode());
    }
}
