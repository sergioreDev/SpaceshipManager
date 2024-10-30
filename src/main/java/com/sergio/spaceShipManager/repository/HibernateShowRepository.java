package com.sergio.spaceShipManager.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sergio.spaceShipManager.model.Show;

public interface HibernateShowRepository  extends JpaRepository<Show, Long>, PagingAndSortingRepository<Show,Long> {
    Show findByName(String name);

    Page<Show> findAll(Pageable pageable);

    Page<Show> findByNameContains(String partialName, Pageable pageable);
}
