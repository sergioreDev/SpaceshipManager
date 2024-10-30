package com.sergio.spaceShipManager.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sergio.spaceShipManager.model.Spaceship;

public interface HibernateSpaceshipRepository  extends JpaRepository<Spaceship, Long>, PagingAndSortingRepository<Spaceship,Long> {
    Spaceship findByName(String name);

    Page<Spaceship> findAll(Pageable pageable);

    Page<Spaceship> findByNameContains(String partialName, Pageable pageable);
}
