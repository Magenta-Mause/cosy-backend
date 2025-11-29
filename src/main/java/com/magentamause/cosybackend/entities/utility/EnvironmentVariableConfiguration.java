package com.magentamause.cosybackend.entities.utility;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class EnvironmentVariableConfiguration {
    @Column(name = "env_key") // Required for h2 databases as 'key' is a reserved word
    private String key;

    @Column(name = "env_value")
    private String value;
}
