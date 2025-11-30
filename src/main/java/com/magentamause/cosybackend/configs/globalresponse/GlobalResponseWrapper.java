package com.magentamause.cosybackend.configs.globalresponse;

import org.springframework.core.MethodParameter;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@RestControllerAdvice
public class GlobalResponseWrapper implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(
            MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true; // apply to all responses
    }

    @Override
    public Object beforeBodyWrite(
            Object body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest request,
            ServerHttpResponse response) {

        String path = request.getURI().getPath();
        if (path.startsWith("/api/v3/api-docs")
                || path.startsWith("/api/swagger-ui")
                || path.startsWith("/api/swagger-ui.html")) {
            return body;
        }

        if (body instanceof byte[]
                || body instanceof Resource
                || body instanceof StreamingResponseBody) {
            return body;
        }

        // Avoid double wrapping
        if (body instanceof ApiResponse) {
            return body;
        }
        HttpStatus status = HttpStatus.OK;

        if (response instanceof ServletServerHttpResponse servletResponse) {
            int statusCode = servletResponse.getServletResponse().getStatus();
            status = HttpStatus.valueOf(statusCode);
        }

        // Handle empty responses (void methods)
        if (body == null) {
            return ApiResponse.builder()
                    .success(true)
                    .statusCode(status.value())
                    .path(path)
                    .build();
        }

        return ApiResponse.builder()
                .data(body)
                .success(true)
                .statusCode(status.value())
                .path(path)
                .build();
    }
}
