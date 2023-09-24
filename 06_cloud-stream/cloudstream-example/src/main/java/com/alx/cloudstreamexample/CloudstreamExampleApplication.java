package com.alx.cloudstreamexample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Function;

@SpringBootApplication
public class CloudstreamExampleApplication {

    private static final Logger log = LoggerFactory.getLogger(CloudstreamExampleApplication.class);


    // // Ya se conecta en auto y en este caso transforma la informacion q emite el producer
    @Bean
    public Function<String, String> toUpperCase() {
        return data -> {
            log.info("==== {} ====", data);

            return data.toUpperCase();
        };
        //return String::toUpperCase;
    }


    public static void main(String[] args) {
        SpringApplication.run(CloudstreamExampleApplication.class, args);
    }

}
