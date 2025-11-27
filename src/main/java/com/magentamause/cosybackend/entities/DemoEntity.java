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
public class DemoEntity {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private String uuid;

    private String name;
}
