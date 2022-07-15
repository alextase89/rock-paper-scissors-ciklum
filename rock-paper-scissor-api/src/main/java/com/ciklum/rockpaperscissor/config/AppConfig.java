package com.ciklum.rockpaperscissor.config;

import com.ciklum.rockpaperscissor.persistence.repository.GameRepository;
import com.ciklum.rockpaperscissor.persistence.repository.GameRepositoryInMemoryImpl;
import com.ciklum.rockpaperscissor.persistence.repository.HistoricalRoundRepository;
import com.ciklum.rockpaperscissor.persistence.repository.HistoricalRoundRepositoryInMemoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class AppConfig {

    @Bean
    public GameRepository gameRepository() {
        return new GameRepositoryInMemoryImpl();
    }

    @Bean
    public HistoricalRoundRepository historicalRoundRepository() {
        return new HistoricalRoundRepositoryInMemoryImpl();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                .allowedMethods("GET", "POST", "PUT", "OPTIONS");
            }
        };
    }

}
