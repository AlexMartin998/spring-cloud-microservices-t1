package com.alex.standalone.clients;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


// // Describe el comportamiento del Balanceo de Carga desde el CLIENT (q es esta app)
// Asi el Balanceo de Carga ya NOOO esta definido x 1 LoadBalancer, sino q c/client puede definir su propia Estrategia de LoadBalancing
@Configuration
public class LoadBalancerConfiguration {

    private static final Logger log = LoggerFactory.getLogger(LoadBalancerConfiguration.class);


    @Bean
    public ServiceInstanceListSupplier discoveryClientServiceInstanceListSupplier(
            ConfigurableApplicationContext context
    ) {
        log.info("Configuration Load balancer to prefer same instance");

        return ServiceInstanceListSupplier.builder()
                .withBlockingDiscoveryClient()
                // sin este si se hace el balanceo de carga repartiendose las req para todas las Instances
//                .withSameInstancePreference()   // sin importar las N instances q tengamos, tododas las Req iran a la misma
                .build(context);
    }

}
