package com.sergio.spaceShipManager.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;


import java.util.Date;

@Data
@Builder
@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Date releaseDate;

    public Movie(Long id, String name, Date releaseDate) {
        this.id = id;
        this.name = name;
        this.releaseDate = releaseDate;
    }

    public Movie() {
    }
}