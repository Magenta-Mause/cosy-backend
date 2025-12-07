package com.magentamause.cosybackend.DTOs;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.magentamause.cosybackend.entities.utility.EnvironmentVariableConfiguration;
import com.magentamause.cosybackend.entities.utility.VolumeMountConfiguration;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GameServerCreationDto {
    @NotBlank private String gameUuid;
    @NotBlank private String serverName;
    @NotBlank private String template;
    @NotBlank private String dockerImageName;
    @NotBlank private String dockerImageTag;
    private Number port;
    @NotBlank private String executionCommand;
    private List<EnvironmentVariableConfiguration> environmentVariables;
    private List<VolumeMountConfiguration> volumeMounts;
}
