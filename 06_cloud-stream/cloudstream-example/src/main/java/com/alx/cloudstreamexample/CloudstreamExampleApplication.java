package com.alx.cloudstreamexample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@SpringBootApplication
public class CloudstreamExampleApplication {

    private static final Logger log = LoggerFactory.getLogger(CloudstreamExampleApplication.class);


    // // Ya se conecta en auto y en este caso transforma la informacion q emite el producer
    // Crea los   TOPICS    con el   NOMBRE de la Fn
    @Bean
    public Function<String, String> toUpperCase() {  // <--  processor
        return data -> {
            log.info("==== {} ====", data);

            return data.toUpperCase();
        };
        //return String::toUpperCase;
    }


    // /// // // Suppliers q siempre emiten data:
    // // // Supplier / Producer   >>   Processor | Function   >>   Consumer      <--   para q funcionen con  CloudStream  deben ser @Bean
    // // producer-out-0
    //@Bean
    public Supplier<Flux<Long>> producer() {
        return () -> Flux.interval(Duration.ofSeconds(1)).log();
    }

    // // processor-in-0  |  processor-out-0
    //@Bean
    public Function<Flux<Long>, Flux<Long>> processor() {
        return flx -> flx.map(n -> n * n);  // return el cuadrado del num q recibe
    }

    //@Bean
    public Consumer<Long> consumer() {  // ya recibe simplemente el Long
        return (number) -> {
            log.info("Message received: '{}'", number);
        };
    }


    public static void main(String[] args) {
        SpringApplication.run(CloudstreamExampleApplication.class, args);
    }

}
