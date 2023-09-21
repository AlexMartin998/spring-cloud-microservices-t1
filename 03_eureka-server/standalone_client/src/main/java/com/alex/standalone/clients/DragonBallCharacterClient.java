package com.alex.standalone.clients;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


// // Interface para CONSUMIR MS con Feign: NOOO requiren q creemos la IMPL xq Feign lo hara x nosotros
// Similar a lo q pasa cuando se W con Spring Data cuando definimos Repositories q no requieren la IMPL
@FeignClient(name = "dragon-ball")  // nombre del MS registrado en Eureka q vamos a CONSUMIR   <--  .properties
@LoadBalancerClient(name = "dragon-ball", configuration = LoadBalancerConfiguration.class)  // Nombre del MS & Class con las configs del Load Balancer
public interface DragonBallCharacterClient {

    // // como si fuera REST
    // @RequestMapping define tanto el HTTP Method como el ENDPOINT q se van a Consumir
    @RequestMapping(method = RequestMethod.GET, value = "/application-name")   // value es el ENDPOINT del MS al q vamos a acceder
    ResponseEntity<String> getApplicationName();

}
