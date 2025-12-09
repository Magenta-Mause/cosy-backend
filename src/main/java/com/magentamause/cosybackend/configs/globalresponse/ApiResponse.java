package com.magentamause.cosybackend.configs.globalresponse;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.time.Instant;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ApiResponse<T> {
    private boolean success;
    private T data;
    private String error;
    private String path;
    @Builder.Default private Instant timestamp = Instant.now();
    private int statusCode;
}
