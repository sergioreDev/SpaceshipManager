package com.sergio.spaceShipManager.service;

import com.sergio.spaceShipManager.repository.HibernateMovieRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sergio.spaceShipManager.model.Movie;

import java.util.Optional;

@Service
public class MovieService {

    private final HibernateMovieRepository movieRepository;

    @Autowired
    public MovieService(HibernateMovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public void deleteAll() {
        movieRepository.deleteAll();
    }

    public Page<Movie> findAll(Pageable page) {
        return movieRepository.findAll(page);
    }

    public Movie save(Movie entity) {
        return movieRepository.save(entity);
    }

    public boolean existsById(Long id) {
        return movieRepository.existsById(id);
    }

    public Optional<Movie> findById(Long aLong) {
        return movieRepository.findById(aLong);
    }

    public void deleteById(Long aLong) {
        movieRepository.deleteById(aLong);
    }

    public Movie findByName(String name) {
        return movieRepository.findByName(name);
    }

    public Page<Movie> findByNameContains(String partialName, Pageable pageable) {
        return movieRepository.findByNameContains(partialName, pageable);
    }
}
