package com.alex.dragonball.controllers;

import com.alex.dragonball.service.FooService;
import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/v1/dragonball/characters")
public class DragonBallController {

    private final Faker faker = new Faker();

    private final List<String> characters = new ArrayList<>();

    @Autowired
    private FooService fooService;

    private static final Logger log = LoggerFactory.getLogger(DragonBallController.class);


    @PostConstruct
    public void init() {
        for (int i = 0; i < 21; i++) {
            characters.add(faker.dragonBall().character());
        }
    }


    @GetMapping
    public ResponseEntity<List<String>> getCharacters() {
        log.info("Getting characters DragonBall");

        // default span
        fooService.printLog();

        // custom span
        fooService.printLogCustomSpan();

        return ResponseEntity.ok(characters);
    }

}
