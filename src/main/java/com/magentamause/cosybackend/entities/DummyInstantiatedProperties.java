package com.magentamause.cosybackend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DummyInstantiatedProperties {
    @Id private String key;
}
