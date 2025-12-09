package com.magentamause.cosybackend.entities.utility;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.Embeddable;
import lombok.*;

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
