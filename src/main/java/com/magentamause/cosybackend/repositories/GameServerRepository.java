package com.magentamause.cosybackend.repositories;

import com.magentamause.cosybackend.entities.GameServerConfigurationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameServerRepository
        extends JpaRepository<GameServerConfigurationEntity, String> {}
