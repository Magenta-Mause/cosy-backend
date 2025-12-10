package com.magentamause.cosybackend.DTOs.actiondtos;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.magentamause.cosybackend.annotations.uniqueElements.UniqueElementsBy;
import com.magentamause.cosybackend.entities.utility.EnvironmentVariableConfiguration;
import com.magentamause.cosybackend.entities.utility.PortMapping;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @UniqueElementsBy(
            fieldNames = {"instancePort", "containerPort"},
            message = "duplicate port mapping")
    @NotNull
    private List<PortMapping> portMappings;

    @NotNull private List<String> executionCommand;

    @UniqueElementsBy(
            fieldNames = {"key", "value"},
            message = "duplicate environment variable")
    private List<EnvironmentVariableConfiguration> environmentVariables;

    @UniqueElementsBy(
            fieldNames = {"hostPath", "containerPath"},
            message = "duplicate volume mounts")
    private List<VolumeMountConfigurationCreationDto> volumeMounts;
}
