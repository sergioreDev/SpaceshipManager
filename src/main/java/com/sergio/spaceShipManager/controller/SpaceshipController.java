package com.sergio.spaceShipManager.controller;

import com.sergio.spaceShipManager.exceptions.ElementNotFoundException;
import com.sergio.spaceShipManager.service.KafkaProducerService;
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

import com.sergio.spaceShipManager.model.Spaceship;

import com.sergio.spaceShipManager.service.SpaceshipService;

@RestController
@RequestMapping("/spaceships")
public class SpaceshipController {

    private SpaceshipService spaceshipService;
    private KafkaProducerService kafkaProducerService;

    @Autowired
    public SpaceshipController(SpaceshipService spaceshipService, KafkaProducerService kafkaProducerService) {
        this.spaceshipService = spaceshipService;
        this.kafkaProducerService = kafkaProducerService;
    }

    private static final Logger logger = LoggerFactory.getLogger(SpaceshipController.class);

    @PostMapping
    public Spaceship createSpaceship(@RequestBody Spaceship spaceship) {
        logger.info("POST /spaceships called, creating new Spaceship");
        validateSpaceship(spaceship);

        Spaceship spaceshipAux = spaceshipService.save(spaceship);
        kafkaProducerService.sendMessage("spaceshipTopic", "A spaceship has been created with id: " + spaceshipAux.getId() + " and name: " +spaceshipAux.getName());
        logger.info("Spaceship created");
        return spaceshipAux;
    }

    @GetMapping("/name")
    @Cacheable("spaceships")
    public Page<Spaceship> searchSpaceship(@RequestParam String name, @PageableDefault(size = 5) Pageable pageable) {
        logger.info("GET /spaceships/name called");
        Page<Spaceship> result = spaceshipService.findByNameContains(name, pageable);

        if (result.isEmpty()) {
            throw new ElementNotFoundException("No spaceships found with name " + name);
        }

        return result.map(spaceship -> {
            logger.info("Spaceship found: " + spaceship.toString());
            return spaceship;
        });
    }

    @GetMapping("/{id}")
    public ResponseEntity<Spaceship> getSpaceshipById(@PathVariable Long id) {
        logger.info("GET /spaceships/"+ id + " called");
        return spaceshipService.findById(id)
                .map(spaceship -> {
                    logger.info("Spaceship found: " + spaceship.toString());
                    return ResponseEntity.ok(spaceship);
                })
                .orElseThrow(() -> new ElementNotFoundException("Spaceship not found with id " + id));
    }

    @GetMapping
    @Cacheable("spaceships")
    public Page<Spaceship> getAllSpaceships(@PageableDefault(size = 5) Pageable page) {
        logger.info("GET /spaceships called");
        return spaceshipService.findAll(page).map(spaceship -> {
            validateSpaceship(spaceship);
            logger.info("Spaceship found: " + spaceship.toString());
            return spaceship;
        });
    }

    @PutMapping("/{id}")
    public ResponseEntity<Spaceship> updateSpaceshipById(@PathVariable Long id, @RequestBody Spaceship spaceship) {
        logger.info("PUT /spaceships/"+ id + " called, updating spaceship: " + spaceship.toString());
        validateSpaceship(spaceship);
        return spaceshipService.findById(id)
                .map(existingSpaceship -> {
                    existingSpaceship.setName(spaceship.getName());
                    Spaceship ship = spaceshipService.save(spaceship);
                    kafkaProducerService.sendMessage("spaceshipTopic", "The name of the spaceship with id: " + ship.getId() + " has been updated to : " +ship.getName());
                    logger.info("Spaceship updated");
                    return ResponseEntity.ok(ship);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpaceshipById(@PathVariable Long id) {
        logger.info("DELETE /spaceships/"+ id + " called, deleting spaceship");
        if (spaceshipService.existsById(id)) {
            spaceshipService.deleteById(id);
            kafkaProducerService.sendMessage("spaceshipTopic", "The spaceship with id: " + id + " has been deleted");
            logger.info("Spaceship deleted");
            return ResponseEntity.ok().build();
        } else {
            throw new ElementNotFoundException("Spaceship with ID " + id + " does not exist");
        }
    }

    public void validateSpaceship(Spaceship spaceship){
        if (spaceship == null) {
            logger.info("The spaceship is null");
            throw new IllegalArgumentException("Spaceship object is null");
        }
        if (spaceship.getName() == null || spaceship.getName().isEmpty()) {
            logger.info("The spaceship is invalid");
            throw new IllegalArgumentException("Spaceship name is required");
        }
    }
}