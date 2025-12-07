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
    @NotBlank String gameUuid;
    @NotBlank String serverName;
    @NotBlank String template;
    @NotBlank String dockerImageName;
    @NotBlank String dockerImageTag;
    Number port;
    @NotBlank String executionCommand;
    List<EnvironmentVariableConfiguration> environmentVariables;
    List<VolumeMountConfiguration> volumeMounts;
}
