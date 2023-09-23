package com.example.gameofthrones.controllerignore;

import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/v1/gameofthrones/characters/ignore-swagger-dirname")
public class IgnoreController {

    private final Faker faker = new Faker();

    private final List<String> characters = new ArrayList<>();

    private static final Logger log = LoggerFactory.getLogger(com.example.gameofthrones.controller.GameOfThronesController.class);


    @PostConstruct
    public void init() {
        for (int i = 0; i < 21; i++) {
            characters.add(faker.gameOfThrones().character());
        }
    }


    @GetMapping
    public ResponseEntity<List<String>> getCharactersIgnoreByDirname() {
        log.info("Getting characters GameOfThrones");

        return ResponseEntity.ok(characters);
    }

}
