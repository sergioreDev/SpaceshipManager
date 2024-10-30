package com.sergio.spaceShipManager.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
@Table(name = "spaceships")
public class Spaceship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Spaceship(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Spaceship() {
    }
}
