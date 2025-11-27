package com.magentamause.cosybackend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
/**
 * JPA entity representing a demo object for API exposure.
 * <p>
 * The {@code uuid} field is the primary key and is generated using the {@link GenerationType#UUID} strategy,
 * ensuring a unique identifier for each entity instance.
 * <p>
 * The {@code name} field represents the business name or label associated with this entity.
 */
public class DemoEntity {
    /**
     * The unique identifier for this entity, generated using UUID strategy.
     */
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private String uuid;

    /**
     * The business name or label for this demo entity.
     */
    private String name;
}
