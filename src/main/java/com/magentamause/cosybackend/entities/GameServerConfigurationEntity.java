package com.magentamause.cosybackend.entities;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.magentamause.cosybackend.entities.utility.EnvironmentVariableConfiguration;
import com.magentamause.cosybackend.entities.utility.PortMapping;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GameServerConfigurationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    private String serverName;

    private String ownerId;

    @Enumerated(EnumType.STRING)
    private GameServerStatus status;

    private LocalDateTime timestampLastStarted;

    private String gameUuid;

    @Column(nullable = false)
    private String dockerImageName;

    private String dockerImageTag;

    @ElementCollection
    @CollectionTable(
            name = "docker_execution_command",
            joinColumns = @JoinColumn(name = "game_server_configuration_uuid"))
    @Column(name = "command_part")
    private List<String> dockerExecutionCommand;

    @ElementCollection
    @CollectionTable(
            name = "port_mappings",
            joinColumns = @JoinColumn(name = "game_server_configuration_uuid"))
    private List<PortMapping> portMappings;

    @ElementCollection
    @CollectionTable(
            name = "environment_variables",
            joinColumns = @JoinColumn(name = "game_server_configuration_uuid"))
    private List<EnvironmentVariableConfiguration> environmentVariables;

    public enum GameServerStatus {
        RUNNING,
        STARTING,
        SHUTTING_DOWN,
        STOPPED,
        FAILED
    }
}
