package com.alex.standalone;

import com.alex.standalone.clients.DragonBallCharacterClient;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.ResponseEntity;

import java.util.List;


@SpringBootApplication
@EnableFeignClients
public class StandaloneApplication implements ApplicationRunner {

    @Autowired
    private EurekaClient eurekaClient; // simplemente se la inyecta

    @Autowired
    private DragonBallCharacterClient dragonBallClient;

    private static final Logger log = LoggerFactory.getLogger(StandaloneApplication.class);


    public static void main(String[] args) {
        SpringApplication.run(StandaloneApplication.class, args);
    }


    // // Feign Client: asi se consume 1 MS con feign (MS registrado en Eureka)
    @Override
    public void run(ApplicationArguments args) throws Exception {
        // simular varias Req al MS
        for (int i = 0; i < 10; i++) {
            ResponseEntity<String> responseEntity = dragonBallClient.getApplicationName();
            log.info("Status {}", responseEntity.getStatusCode());

            String body = responseEntity.getBody();
            log.info("Body {}", body);
        }
    }


    /**    Implementacion del CLIENT de Eureka
     // // RUN: nos permitira ejecutar Logica despues de q la App de Spring se haya iniciado
     // Como no es un REST API, simplemente tras ejecutarse esta logica, la app se bajara
     @Override public void run(ApplicationArguments args) throws Exception {
     // // // consumir informacion de Eureka en este CLIENT
     Application application = eurekaClient.getApplication("dragon-ball");// tal cual tiene el application.name el MS q queremos consumir  <--  .properties

     log.info("Application name: {}", application.getName());

     // // instancias en ejecucion del MS
     List<InstanceInfo> instances = application.getInstances();
     for (InstanceInfo instanceInfo : instances) {
     log.info("Ip Address {}:{}", instanceInfo.getIPAddr(), instanceInfo.getPort());
     }
     }
     */


}
