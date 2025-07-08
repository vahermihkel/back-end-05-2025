package ee.mihkel.webshop.config;

import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.web.client.RestTemplateBuilderConfigurer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Random;
import java.util.Scanner;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

    // Random random = new Random()
//    @Bean
//    public Random getRandom() {
//        return new Random();
//    }
//
//    @Bean
//    public Scanner getScanner() {
//        return new Scanner(System.in);
//    }

    @Bean
    public RestTemplate getRestTemplate(RestTemplateBuilder builder) {
        builder.connectTimeout(Duration.ofMinutes(1));
        return builder.build();
//        return new RestTemplate();
    }
}
