package com.example.gameofthrones.controller;

import com.example.gameofthrones.service.TranslationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RestController
@RequestMapping("/translations")
public class MessageController {

    @Autowired
    private TranslationService translationService;

    private static final Logger log = LoggerFactory.getLogger(MessageController.class);


    @GetMapping
    public ResponseEntity<String> getTranslations(@RequestParam("message") String message) {

        log.info("Message received: '{}' | start", message);

        Optional<String> translation = translationService.getTranslation(message);
        if (translation.isPresent()) return ResponseEntity.ok(message);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
