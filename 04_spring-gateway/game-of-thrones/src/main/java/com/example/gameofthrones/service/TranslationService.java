package com.example.gameofthrones.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Service
public class TranslationService {

    public Map<String, String> words = new HashMap<>();

    private static final Logger log = LoggerFactory.getLogger(TranslationService.class);

    @PostConstruct
    public void init() {
        words.put("Hello", "Hola");
        words.put("Bye", "Adios");
        words.put("Word", "Palabra");
    }


    // // W con cache en Memoria: Si ya esta Cacheado, NOO toca esta logica
    @Cacheable("translations")  //   <--  CacheConfig > ConcurrentMapCacheManager
    public Optional<String> getTranslation(String message) {
        log.info(" ====== Get Translation ====== ");

        for (String word : words.keySet()) {
            try {
                Thread.sleep(1000L);  // simulate a slow operation
            } catch (InterruptedException ignored) {
            }

            if (word.equals(message)) return Optional.of(words.get(message));
        }

        return Optional.empty();
    }

    @CacheEvict("translations")  // nombre del CACHE q queremos Limpiar  <--  CacheConfig > ConcurrentMapCacheManager
    public void clearCache(String message) {
    }

}
