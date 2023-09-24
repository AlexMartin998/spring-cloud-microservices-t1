package com.alx.cloudstreamexample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/messages")
public class MessageController {

    // // Poder     Publicar Eventos    a 1 Topic cuando se W con Cloud Stream
    @Autowired
    private StreamBridge streamBridge;


    // // Para Publicar 1 Evento cuando se hace algo en especifico, o sea como debe de ser en la vida real
    // Con kafka se puede usar 1    Kafka Template    para PRODUCIR/PUBLICAR  eventos, pero como estamos W con   CloudStream   usaremos  `StreamBridge`
    @PostMapping
    public void postMessage(@RequestBody String message) {
        // se lo  ENVIO  al   (TOPIC, Body)
        streamBridge.send("toUpperCase-in-0", message);
    }

}
