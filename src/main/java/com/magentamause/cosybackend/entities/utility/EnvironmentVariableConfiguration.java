package com.magentamause.cosybackend.entities.utility;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class EnvironmentVariableConfiguration {
    @Column(name = "env_key") // Required for h2 databases as 'key' is a reserved word
    private String key;

    @Column(name = "env_value")
    private String value;
}
