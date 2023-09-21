package com.alex.dragonballfailover.controller;

import com.github.javafaker.Faker;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/v1/db-failover/dragonball/characters")
public class DragonBallController {

    private final Faker faker = new Faker();

    private final List<String> characters = new ArrayList<>();

    private static final Logger log = LoggerFactory.getLogger(DragonBallController.class);


    @PostConstruct
    public void init() {
        for (int i = 0; i < 21; i++) {
            characters.add(String.format("Failover - %s", faker.dragonBall().character()));
        }
    }


    @GetMapping
    public ResponseEntity<List<String>> getCharacters() {
        log.info("Getting characters DragonBall");

        return ResponseEntity.ok(characters);
    }

}
