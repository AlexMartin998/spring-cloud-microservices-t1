package com.alex.springgateway.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
public class WebClientConfig {


    // // Esta otra forma de crear 1     REST Client      como rest template con   Programacion Reactiva   (reactor)
    @Bean
    @LoadBalanced       // para hacer Client Load Balancing
    public WebClient.Builder webClient() {
        return WebClient.builder();
    }


}
