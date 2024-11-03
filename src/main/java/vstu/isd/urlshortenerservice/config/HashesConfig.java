package vstu.isd.urlshortenerservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class HashesConfig {
    @Value("${spring.hashes.hash-pool-size}")
    private int hashPoolSize;

    @Value("${spring.hashes.pool-exhaustion-percentage}")
    private int exhaustionPercentage;

    @Value("${spring.hashes.generation-thread-pool-size}")
    private int generationThreadPoolSize;

    @Value("${spring.hashes.pool-exhaustion-percentage}")
    private int exhaustionPercentageExhaustion;

    @Bean
    public int HASH_POOL_SIZE() {
        return hashPoolSize;
    }

    @Bean
    public int EXHAUSTION_POOL_PERCENTAGE() {
        return exhaustionPercentage;
    }

    @Bean
    public ExecutorService hashGeneratorThreadPool() {
        return Executors.newFixedThreadPool(generationThreadPoolSize);
    }
}
