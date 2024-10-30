package com.sergio.spaceShipManager.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sergio.spaceShipManager.model.Movie;

public interface HibernateMovieRepository  extends JpaRepository<Movie, Long>, PagingAndSortingRepository<Movie,Long> {
    Movie findByName(String name);

    Page<Movie> findAll(Pageable pageable);

    Page<Movie> findByNameContains(String partialName, Pageable pageable);
}
