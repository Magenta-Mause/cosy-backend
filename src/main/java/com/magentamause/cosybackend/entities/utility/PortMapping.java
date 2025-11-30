package com.magentamause.cosybackend.entities.utility;

import jakarta.persistence.Embeddable;
import lombok.*;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PortMapping {
    private int instancePort;
    private int containerPort;
}
