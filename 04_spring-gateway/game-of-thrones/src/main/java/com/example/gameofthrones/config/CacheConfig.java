package com.example.gameofthrones.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


@EnableCaching
@Configuration
public class CacheConfig {

    // // // W con Redis: Como es externo, tarda 1 poco mas x la Latencia
    // definimos el Cliente de Redis
    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson() {
        Config config = new Config();

        config
                .useSingleServer()  // 1 solo server xq no estamos W con clusters
                .setAddress("redis://127.0.0.1:6379");  // donde corre redis

        return Redisson.create(config);
    }

    @Bean
    public CacheManager getManager(RedissonClient redissonClient) { // Inyectamos el  Cliente de Redis configurado arriba
        // // W with Redis
        Map<String, CacheConfig> config = new HashMap<>();
        config.put("translations", new CacheConfig());  // name to use in impls that require caching

        return new RedissonSpringCacheManager(redissonClient);
    }


    // // // Cache en MEMORIA:  Es mas rapido q con Cache Externo, pero tiene sus limitaciones.
    /* W con Cache en memoria
    @Bean
    public CacheManager getManager() {
        return new ConcurrentMapCacheManager("translations");  // name to use in implementations that require caching
    }
    */

}

