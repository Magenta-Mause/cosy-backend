package com.magentamause.cosybackend.repositories;

import com.magentamause.cosybackend.entities.DummyInstantiatedProperties;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DummyInstantiatedPropertiesRepository
        extends JpaRepository<DummyInstantiatedProperties, String> {}
