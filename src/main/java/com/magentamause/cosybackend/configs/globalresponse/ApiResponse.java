package com.magentamause.cosybackend.configs.globalresponse;

import java.time.Instant;
import lombok.Builder;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

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
