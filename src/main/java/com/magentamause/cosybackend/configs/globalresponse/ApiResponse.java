package com.magentamause.cosybackend.configs.globalresponse;

import java.time.Instant;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse<T> {
    private boolean success;
    private T data;
    private String error;
    @Builder.Default private Instant timestamp = Instant.now();
}
