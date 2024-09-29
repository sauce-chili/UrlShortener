package vstu.isd.urlshortenerservice;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableRetry
@EnableCaching
public class UrlShortenerApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(UrlShortenerApplication.class)
                .bannerMode(Banner.Mode.OFF)
                .run(args);
    }
}
