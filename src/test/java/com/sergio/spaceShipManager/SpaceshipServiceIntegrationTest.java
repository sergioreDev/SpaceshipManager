package com.sergio.spaceShipManager;

import com.sergio.spaceShipManager.SpaceShipManagerApplication;
import com.sergio.spaceShipManager.model.Spaceship;
import com.sergio.spaceShipManager.service.SpaceshipService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SpaceShipManagerApplication.class)
@TestPropertySource(locations = "classpath:test.properties")
public class SpaceshipServiceIntegrationTest {

    @Autowired
    private SpaceshipService spaceshipService;

    @Test
    public void testInitialRecordsExist() {
        assertNotNull(spaceshipService.findByName("Millenium Falcon"));
    }

    @Test
    public void testFindByName() {
        assertNotNull(spaceshipService.findByName("Tardis"));
    }

    @Test
    public void testFindAll() {
        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<Spaceship> page = spaceshipService.findAll(pageRequest);

        assertNotNull(page);
        assertFalse(page.getContent().isEmpty());
    }

    @Test
    public void testDeleteById() {
        Spaceship spaceship = spaceshipService.findByName("Elysium");
        spaceshipService.deleteById(spaceship.getId());

        assertNull(spaceshipService.findByName("Elysium"));
    }

    @Test
    public void testSave() {
        Spaceship newSpaceship = new Spaceship();
        newSpaceship.setName("Spaceship Test");

        spaceshipService.save(newSpaceship);

        assertNotNull(spaceshipService.findByName("Spaceship Test"));
    }

    @Test
    public void testExistsById() {
        Spaceship spaceship = spaceshipService.findByName("Millenium Falcon");

        assertNotNull(spaceship);
        assertTrue(spaceshipService.existsById(spaceship.getId()));
    }

    @Test
    public void testFindById() {
        Spaceship spaceship = spaceshipService.findByName("Millenium Falcon");

        assertNotNull(spaceship);
        Optional<Spaceship> foundSpaceship = spaceshipService.findById(spaceship.getId());

        assertTrue(foundSpaceship.isPresent());
        assertEquals(foundSpaceship.get().getName(), "Millenium Falcon");
    }

    @Test
    public void testFindByNameContains() {
        // Asegúrate de tener al menos alguna nave espacial con 'Fal' en alguna parte del nombre para que esta prueba funcione.
        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<Spaceship> page = spaceshipService.findByNameContains("Fal", pageRequest);

        assertNotNull(page);
        assertFalse(page.getContent().isEmpty());
    }
}
