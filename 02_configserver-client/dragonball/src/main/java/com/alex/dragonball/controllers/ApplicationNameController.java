package com.alex.dragonball.controllers;

import com.alex.dragonball.config.DragonBallConfig;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;


@RestController
@RequestMapping("/application-name")
public class ApplicationNameController {

    @Autowired
    private DragonBallConfig configuration;

    // // Metrics  <--  X las deps, Spring ya sabe en donde esta este Bean, asi q simplemente lo Inyectamos
    @Autowired
    private MeterRegistry meterRegistry;

    private static final Logger log = LoggerFactory.getLogger(ApplicationNameController.class);


    @GetMapping
    @Timed("alx.dragonball.name.get")   // times solo para Controller Methods  <--  /actuator/metrics/alx.dragonball.name.get
    public ResponseEntity<String> getAppName() {
        log.info("Getting Application Name");


        // // custom metrics: metric name, tags in pairs key-value
        int value = new Random().nextInt(20);

        // condicionar el Value del TAG de esta metric  | consultar x tag: actuator/metrics/alx.dragonball.name?tag=level:jr
        meterRegistry.counter("alx.dragonball.name", "level", (value < 3) ? "jr" : "sr")
                .increment(value);  // incrementamos el counter con 1 random


        return ResponseEntity.ok(configuration.getApplicationName());
    }

}
