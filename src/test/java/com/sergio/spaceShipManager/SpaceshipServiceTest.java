package com.sergio.spaceShipManager;

import com.sergio.spaceShipManager.model.Spaceship;
import com.sergio.spaceShipManager.repository.HibernateSpaceshipRepository;
import com.sergio.spaceShipManager.service.SpaceshipService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

public class SpaceshipServiceTest {

    @Test
    public void testFindAll() {
        // Preparación
        HibernateSpaceshipRepository mockRepository = mock(HibernateSpaceshipRepository.class);
        SpaceshipService spaceshipService = new SpaceshipService(mockRepository);
        Pageable pageable = mock(Pageable.class);

        // Prueba
        Page<Spaceship> result = spaceshipService.findAll(pageable);

        // Verificación
        // No hacemos nada, solo queremos asegurarnos de que no arroje ninguna excepción.
    }

    @Test
    public void testSave() {
        // Preparación
        HibernateSpaceshipRepository mockRepository = mock(HibernateSpaceshipRepository.class);
        SpaceshipService spaceshipService = new SpaceshipService(mockRepository);
        Spaceship mockSpaceship = new Spaceship(0L, "Spaceball One");

        when(mockRepository.save(mockSpaceship)).thenReturn(mockSpaceship);

        // Prueba
        Spaceship result = spaceshipService.save(mockSpaceship);

        // Verificación
        assertEquals(mockSpaceship, result);
    }

    @Test
    public void testExistsById() {
        // Preparación
        HibernateSpaceshipRepository mockRepository = mock(HibernateSpaceshipRepository.class);
        SpaceshipService spaceshipService = new SpaceshipService(mockRepository);

        when(mockRepository.existsById(1L)).thenReturn(true);

        // Prueba
        boolean result = spaceshipService.existsById(1L);

        // Verificación
        assertTrue(result);
    }

    @Test
    public void testFindById() {
        // Preparación
        HibernateSpaceshipRepository mockRepository = mock(HibernateSpaceshipRepository.class);
        SpaceshipService spaceshipService = new SpaceshipService(mockRepository);
        Spaceship mockSpaceship = new Spaceship(1L, "Millenium Falcon");

        when(mockRepository.findById(1L)).thenReturn(Optional.of(mockSpaceship));

        // Prueba
        Optional<Spaceship> result = spaceshipService.findById(1L);

        // Verificación
        assertTrue(result.isPresent());
        assertEquals(mockSpaceship, result.get());
    }

    @Test
    public void testDeleteById() {
        // Preparación
        HibernateSpaceshipRepository mockRepository = mock(HibernateSpaceshipRepository.class);
        SpaceshipService spaceshipService = new SpaceshipService(mockRepository);

        // Prueba
        spaceshipService.deleteById(1L);

        // Verificación
        verify(mockRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testFindByName() {
        // Preparación
        HibernateSpaceshipRepository mockRepository = mock(HibernateSpaceshipRepository.class);
        SpaceshipService spaceshipService = new SpaceshipService(mockRepository);
        Spaceship mockSpaceship = new Spaceship(1L, "Millenium Falcon");

        when(mockRepository.findByName("Millenium Falcon")).thenReturn(mockSpaceship);

        // Prueba
        Spaceship result = spaceshipService.findByName("Millenium Falcon");

        // Verificación
        assertEquals(mockSpaceship, result);
    }

    @Test
    public void testFindByNameContains() {
        // Preparación
        HibernateSpaceshipRepository mockRepository = mock(HibernateSpaceshipRepository.class);
        SpaceshipService spaceshipService = new SpaceshipService(mockRepository);
        Pageable pageable = mock(Pageable.class);

        // Prueba
        Page<Spaceship> result = spaceshipService.findByNameContains("Test", pageable);

        // Verificación
        // No hacemos nada, solo queremos asegurarnos de que no arroje ninguna excepción.
    }
}
