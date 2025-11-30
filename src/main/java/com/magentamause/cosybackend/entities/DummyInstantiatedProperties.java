package com.magentamause.cosybackend.entities;

import jakarta.persistence.Column;
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
    @Column(name = "value_key") // Required for h2 databases as 'key' is a reserved word
    @Id
    private String key;
}
