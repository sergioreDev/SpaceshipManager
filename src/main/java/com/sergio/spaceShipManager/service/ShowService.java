package com.sergio.spaceShipManager.service;

import com.sergio.spaceShipManager.repository.HibernateShowRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sergio.spaceShipManager.model.Show;

import java.util.Optional;

@Service
public class ShowService {

    private final HibernateShowRepository ShowRepository;

    @Autowired
    public ShowService(HibernateShowRepository ShowRepository) {
        this.ShowRepository = ShowRepository;
    }

    public void deleteAll() {
        ShowRepository.deleteAll();
    }

    public Page<Show> findAll(Pageable page) {
        return ShowRepository.findAll(page);
    }

    public Show save(Show entity) {
        return ShowRepository.save(entity);
    }

    public boolean existsById(Long id) {
        return ShowRepository.existsById(id);
    }

    public Optional<Show> findById(Long aLong) {
        return ShowRepository.findById(aLong);
    }

    public void deleteById(Long aLong) {
        ShowRepository.deleteById(aLong);
    }

    public Show findByName(String name) {
        return ShowRepository.findByName(name);
    }

    public Page<Show> findByNameContains(String partialName, Pageable pageable) {
        return ShowRepository.findByNameContains(partialName, pageable);
    }
}
