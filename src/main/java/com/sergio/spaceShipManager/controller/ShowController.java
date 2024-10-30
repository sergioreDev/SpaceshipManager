package com.sergio.spaceShipManager.controller;

import com.sergio.spaceShipManager.exceptions.ElementNotFoundException;
import com.sergio.spaceShipManager.model.Show;
import com.sergio.spaceShipManager.service.KafkaProducerService;
import com.sergio.spaceShipManager.service.ShowService;
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

import com.sergio.spaceShipManager.model.Show;

import com.sergio.spaceShipManager.service.ShowService;

@RestController
@RequestMapping("/shows")
public class ShowController {

    private ShowService showService;
    private KafkaProducerService kafkaProducerService;

    @Autowired
    public ShowController(ShowService showService, KafkaProducerService kafkaProducerService) {
        this.showService = showService;
        this.kafkaProducerService = kafkaProducerService;
    }

    private static final Logger logger = LoggerFactory.getLogger(ShowController.class);

    @PostMapping
    public Show createShow(@RequestBody Show show) {
        logger.info("POST /shows called, creating new Show");
        validateShow(show);

        Show showAux = showService.save(show);
        kafkaProducerService.sendMessage("showTopic", "A show has been created with id: " + showAux.getId() + " and name: " +showAux.getName());
        logger.info("Show created");
        return showAux;
    }

    @GetMapping("/name")
    @Cacheable("shows")
    public Page<Show> searchShow(@RequestParam String name, @PageableDefault(size = 5) Pageable pageable) {
        logger.info("GET /shows/name called");
        Page<Show> result = showService.findByNameContains(name, pageable);

        if (result.isEmpty()) {
            throw new ElementNotFoundException("No shows found with name " + name);
        }

        return result.map(show -> {
            logger.info("Show found: " + show.toString());
            return show;
        });
    }

    @GetMapping("/{id}")
    public ResponseEntity<Show> getShowById(@PathVariable Long id) {
        logger.info("GET /shows/"+ id + " called");
        return showService.findById(id)
                .map(show -> {
                    logger.info("Show found: " + show.toString());
                    return ResponseEntity.ok(show);
                })
                .orElseThrow(() -> new ElementNotFoundException("Show not found with id " + id));
    }

    @GetMapping
    @Cacheable("shows")
    public Page<Show> getAllShows(@PageableDefault(size = 5) Pageable page) {
        logger.info("GET /shows called");
        return showService.findAll(page).map(show -> {
            validateShow(show);
            logger.info("Show found: " + show.toString());
            return show;
        });
    }

    @PutMapping("/{id}")
    public ResponseEntity<Show> updateShowById(@PathVariable Long id, @RequestBody Show show) {
        logger.info("PUT /shows/"+ id + " called, updating show: " + show.toString());
        validateShow(show);
        return showService.findById(id)
                .map(existingShow -> {
                    existingShow.setName(show.getName());
                    Show showAux = showService.save(show);
                    kafkaProducerService.sendMessage("showTopic", "The show with id: " + showAux.getId() + " has been updated to: " +showAux.getName());
                    logger.info("Show created");
                    return ResponseEntity.ok(showAux);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShowById(@PathVariable Long id) {
        logger.info("DELETE /shows/"+ id + " called, deleting show");
        if (showService.existsById(id)) {
            showService.deleteById(id);
            kafkaProducerService.sendMessage("showTopic", "The show with id: " + id + " has been deleted");
            logger.info("Show deleted");
            return ResponseEntity.ok().build();
        } else {
            throw new ElementNotFoundException("Show with ID " + id + " does not exist");
        }
    }

    public void validateShow(Show show){
        if (show == null) {
            logger.info("The show is null");
            throw new IllegalArgumentException("Show object is null");
        }
        if (show.getName() == null || show.getName().isEmpty()) {
            logger.info("The show is invalid");
            throw new IllegalArgumentException("Show name is required");
        }
    }
}