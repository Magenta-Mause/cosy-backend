package com.magentamause.cosybackend.repositories;

import com.magentamause.cosybackend.entities.DemoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemoEntityRepository extends JpaRepository<DemoEntity, String> {
}
