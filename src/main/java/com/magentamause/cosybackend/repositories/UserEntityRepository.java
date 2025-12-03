package com.magentamause.cosybackend.repositories;

import com.magentamause.cosybackend.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEntityRepository extends JpaRepository<UserEntity, String> {}
