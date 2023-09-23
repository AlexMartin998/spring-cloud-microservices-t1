package com.example.gameofthrones.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


// // W con Cache en MEMORIA
@EnableCaching
@Configuration
public class CacheConfig {

    @Bean
    public CacheManager getManager() {
        return new ConcurrentMapCacheManager("translations");  // name to use in implementations that require caching
    }

}

