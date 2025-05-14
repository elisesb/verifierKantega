package com.example.verifiserer.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Denne klassen konfigurerer CORS (Cross-Origin Resource Sharing) for applikasjonen.
 * Ved å implementere WebMvcConfigurer-grensesnittet kan vi tilpasse hvordan Spring MVC håndterer
 * spesifikke aspekter av webapplikasjonen, som her hvor vi konfigurerer regler for hvilke domener
 * og HTTP-metoder som har tilgang til API-et.
 *
 * CORS-konfigurasjonen her tillater forespørsler til alle API-endepunkter som starter med "/api/"
 * fra en spesifisert frontend-URL (f.eks. "http://localhost:3000") og støtter metodene GET, POST, PUT og DELETE.
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:3000") // Legg til din frontend-URL her
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}