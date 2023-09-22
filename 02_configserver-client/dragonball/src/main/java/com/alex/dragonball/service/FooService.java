package com.alex.dragonball.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.stereotype.Service;
import org.springframework.cloud.sleuth.Tracer;


@Service
public class FooService {

    // // create Custom Spans
    @Autowired
    private Tracer tracer;


    private static final Logger log = LoggerFactory.getLogger(FooService.class);


    // // default span
    public void printLog() {
        log.info("Test log");
    }


    // // custom spam (new Span ID)
    public void printLogCustomSpan() {
        // creo el nuevo span
        Span newSpan = tracer.nextSpan().name("newSpan");  // name del custom spam (new span id)

        // INICIO el nuevo span
        try (Tracer.SpanInScope ws = tracer.withSpan(newSpan.start())) {
            log.info("Tes log  |  Custom Span  <--  newSpan");
        } finally {
            // finalizo el span
            newSpan.end();
        }
    }

}
