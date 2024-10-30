package com.sergio.spaceShipManager;

import com.sergio.spaceShipManager.model.Spaceship;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpaceshipUnitTest {

    @Test
    public void testConstructor() {
        Spaceship spaceship = new Spaceship(1L, "Halcón milenario");
        assertEquals(1L, spaceship.getId());
        assertEquals("Halcón milenario", spaceship.getName());
    }

    @Test
    public void testSettersAndGetters() {
        Spaceship spaceship = new Spaceship();
        spaceship.setId(2L);
        spaceship.setName("Tardis");
        assertEquals(2L, spaceship.getId());
        assertEquals("Tardis", spaceship.getName());
    }

    @Test
    public void testEquality() {
        Spaceship spaceship1 = new Spaceship(1L, "Halcón milenario");
        Spaceship spaceship2 = new Spaceship(1L, "Halcón milenario");
        assertEquals(spaceship1, spaceship2);
    }

    @Test
    public void testHashCode() {
        Spaceship spaceship1 = new Spaceship(1L, "Halcón milenario");
        Spaceship spaceship2 = new Spaceship(1L, "Halcón milenario");
        assertEquals(spaceship1.hashCode(), spaceship2.hashCode());
    }

    @Test
    public void testBuilder() {
        Spaceship spaceship = Spaceship.builder().id(1L).name("Halcón milenario").build();
        assertEquals(1L, spaceship.getId());
        assertEquals("Halcón milenario", spaceship.getName());
    }
}
