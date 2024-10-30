package com.sergio.spaceShipManager.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@Entity
@Table(name = "shows")
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Date releaseDate;
    private int amountOfSeasons;

    public Show(Long id, String name, Date releaseDate, int amountOfSeasons) {
        this.id = id;
        this.name = name;
        this.releaseDate = releaseDate;
        this.amountOfSeasons = amountOfSeasons;
    }

    public Show() {
    }
}