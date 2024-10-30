package com.sergio.spaceShipManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpaceShipManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpaceShipManagerApplication.class, args);
	}

}
