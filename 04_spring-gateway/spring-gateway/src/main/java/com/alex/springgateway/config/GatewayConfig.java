package com.alex.springgateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


// // reglas del Gateway
@Configuration
public class GatewayConfig {

    // // Gateway SIMPLE q NOOO usa Eureka: Si se generan Nuevas Instancias de algun MS, NOOO seran incluidas en este Enrutamiento
    @Bean
    @Profile("localhostRouter-noEureka")
    public RouteLocator configLocalNoEureka(RouteLocatorBuilder builder) {

        return builder.routes()
                .route(route -> route
                        .path("/api/v1/dragonball/*") // el trafico q ingrese en este Path se REDIRECCIONARA con el mismo path a la URL del .uri()
                        .uri("http://localhost:8082") // a donde redirigiremos con el mismo Path
                )
                .route(route -> route
                        .path("/api/v1/gameofthrones/*") // toda req hacia este path lo voy a REDIRIGIR al   .uir()
                        .uri("http://localhost:8083") // a donde redirigimos lo del  .path   manteniendo dicho path
                )
                .build();
    }


    // // // Con Eureka: Usa el nombre del MS registrado en Eureka
    @Bean
    @Profile("localhost-eureka")   // Los Profiles requiren ser   HABILITADOS   en el   .properties
    public RouteLocator configLocalEureka(RouteLocatorBuilder builder) {

        return builder.routes()
                .route(route -> route
                        .path("/api/v1/dragonball/*") // mismo Path de MS
                        .uri("lb://dragon-ball")  // application.name del MS (nombre con el q esta registrado en Eureka)
                )
                .route(route -> route
                        .path("/api/v1/gameofthrones/*")
                        .uri("lb://alx-game-of-thrones")
                )
                .build();
    }


    // // // Eureka + Circuit Breaking + Failover
    @Bean
    @Profile("localhost-eureka-cb")   // Los Profiles requiren ser   HABILITADOS   en el   .properties
    public RouteLocator configLocalEurekaCircuitBraking(RouteLocatorBuilder builder) {

        return builder.routes()
                .route(route -> route
                        .path("/api/v1/dragonball/*") // mismo Path de MS
                        .filters( // si cae 1 Req a este path+uri del MS y NO funciona, lo redirecciona a esta otra URL:
                                f -> f.circuitBreaker(
                                        cb -> cb.setName("failoverCB")
                                                // url a la q va a redireccionar en caso d q el MS falle
                                                .setFallbackUri("forward:/api/v1/db-failover/dragonball/characters") // <---
                                                .setRouteId("dbFailover")
                                )
                        )
                        .uri("lb://dragon-ball")  // application.name del MS (nombre con el q esta registrado en Eureka)
                )
                .route(route -> route
                        .path("/api/v1/gameofthrones/*")
                        .uri("lb://alx-game-of-thrones")
                )
                .route(route -> route
                        .path("/api/v1/db-failover/dragonball/characters")
                        .uri("lb://alx-dragonball-failover")      // <---
                )
                .build();
    }

}
