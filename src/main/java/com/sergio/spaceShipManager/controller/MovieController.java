package com.sergio.spaceShipManager.controller;

import com.sergio.spaceShipManager.exceptions.ElementNotFoundException;
import com.sergio.spaceShipManager.model.Movie;
import com.sergio.spaceShipManager.service.KafkaProducerService;
import com.sergio.spaceShipManager.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sergio.spaceShipManager.model.Movie;

import com.sergio.spaceShipManager.service.MovieService;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private MovieService movieService;
    private KafkaProducerService kafkaProducerService;

    @Autowired
    public MovieController(MovieService movieService, KafkaProducerService kafkaProducerService) {
        this.movieService = movieService;
        this.kafkaProducerService = kafkaProducerService;
    }

    private static final Logger logger = LoggerFactory.getLogger(MovieController.class);

    @PostMapping
    public Movie createMovie(@RequestBody Movie movie) {
        logger.info("POST /movies called, creating new Movie");
        validateMovie(movie);

        Movie movieAux = movieService.save(movie);
        kafkaProducerService.sendMessage("movieTopic", "The movie with id: " + movieAux.getId() + " and name: " +movieAux.getName() + " has been created");
        logger.info("Movie created");
        return movieAux;
    }

    @GetMapping("/name")
    @Cacheable("movies")
    public Page<Movie> searchMovie(@RequestParam String name, @PageableDefault(size = 5) Pageable pageable) {
        logger.info("GET /movies/name called");
        Page<Movie> result = movieService.findByNameContains(name, pageable);

        if (result.isEmpty()) {
            throw new ElementNotFoundException("No movies found with name " + name);
        }

        return result.map(movie -> {
            logger.info("Movie found: " + movie.toString());
            return movie;
        });
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        logger.info("GET /movies/"+ id + " called");
        return movieService.findById(id)
                .map(movie -> {
                    logger.info("Movie found: " + movie.toString());
                    return ResponseEntity.ok(movie);
                })
                .orElseThrow(() -> new ElementNotFoundException("Movie not found with id " + id));
    }

    @GetMapping
    @Cacheable("movies")
    public Page<Movie> getAllMovies(@PageableDefault(size = 5) Pageable page) {
        logger.info("GET /movies called");
        return movieService.findAll(page).map(movie -> {
            validateMovie(movie);
            logger.info("Movie found: " + movie.toString());
            return movie;
        });
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovieById(@PathVariable Long id, @RequestBody Movie movie) {
        logger.info("PUT /movies/"+ id + " called, updating movie: " + movie.toString());
        validateMovie(movie);
        return movieService.findById(id)
                .map(existingMovie -> {
                    existingMovie.setName(movie.getName());
                    Movie movieAux = movieService.save(movie);
                    kafkaProducerService.sendMessage("movieTopic", "The movie with id: " + movieAux.getId() + " has been updated to: " +movieAux.getName());
                    logger.info("Movie created");
                    return ResponseEntity.ok(movieAux);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovieById(@PathVariable Long id) {
        logger.info("DELETE /movies/"+ id + " called, deleting movie");
        if (movieService.existsById(id)) {
            movieService.deleteById(id);
            kafkaProducerService.sendMessage("movieTopic", "The movie with id: " + id + " has been deleted");

            logger.info("Movie deleted");
            return ResponseEntity.ok().build();
        } else {
            throw new ElementNotFoundException("Movie with ID " + id + " does not exist");
        }
    }

    public void validateMovie(Movie movie){
        if (movie == null) {
            logger.info("The movie is null");
            throw new IllegalArgumentException("Movie object is null");
        }
        if (movie.getName() == null || movie.getName().isEmpty()) {
            logger.info("The movie is invalid");
            throw new IllegalArgumentException("Movie name is required");
        }
    }
}