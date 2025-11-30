package com.magentamause.cosybackend.configs.globalresponse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiResponse<?>> handleResponseStatusException(
            ResponseStatusException ex, HttpServletRequest request) {
        log.warn("Response status exception occurred", ex);
        return ResponseEntity.status(ex.getStatusCode())
                .body(
                        ApiResponse.builder()
                                .success(false)
                                .data(ex.getReason())
                                .error(ex.getMessage())
                                .path(request.getRequestURI())
                                .statusCode(ex.getStatusCode().value())
                                .build());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFound(NoResourceFoundException ex) {
        log.warn("Resource not found: \"{}\"", ex.getResourcePath());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        ApiResponse.builder()
                                .success(false)
                                .data("Resource not found")
                                .error(
                                        "404 No Resource found under \""
                                                + ex.getResourcePath()
                                                + '"')
                                .path(ex.getResourcePath())
                                .statusCode(HttpStatus.NOT_FOUND.value())
                                .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handle(Exception ex, HttpServletRequest request)
            throws Exception {
        String path = request.getRequestURI();
        log.warn("Unexpected error occurred", ex);

        if (path.startsWith("/api/v3/api-docs") || path.startsWith("/api/swagger-ui")) {
            throw ex;
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ApiResponse.builder()
                                .success(false)
                                .data("An unexpected error occurred.")
                                .path(path)
                                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .build());
    }
}
