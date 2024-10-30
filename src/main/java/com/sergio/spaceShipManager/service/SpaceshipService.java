package com.sergio.spaceShipManager.service;

import com.sergio.spaceShipManager.repository.HibernateSpaceshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sergio.spaceShipManager.model.Spaceship;

import java.util.Optional;

@Service
public class SpaceshipService {

    private final HibernateSpaceshipRepository spaceshipRepository;

    @Autowired
    public SpaceshipService(HibernateSpaceshipRepository spaceshipRepository) {
        this.spaceshipRepository = spaceshipRepository;
    }

    public void deleteAll() {
        spaceshipRepository.deleteAll();
    }

    public Page<Spaceship> findAll(Pageable page) {
        return spaceshipRepository.findAll(page);
    }

    public Spaceship save(Spaceship entity) {
        return spaceshipRepository.save(entity);
    }

    public boolean existsById(Long id) {
        return spaceshipRepository.existsById(id);
    }

    public Optional<Spaceship> findById(Long aLong) {
        return spaceshipRepository.findById(aLong);
    }

    public void deleteById(Long aLong) {
        spaceshipRepository.deleteById(aLong);
    }

    public Spaceship findByName(String name) {
        return spaceshipRepository.findByName(name);
    }

    public Page<Spaceship> findByNameContains(String partialName, Pageable pageable) {
        return spaceshipRepository.findByNameContains(partialName, pageable);
    }
}
