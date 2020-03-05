package com.lab2webservices.lab2webservices;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(PhoneRepository phoneRepo, BrandRepository brandRepo) {
        return args -> {
            phoneRepo.save(new Phone(1, "Iphone"));
            phoneRepo.save(new Phone(2, "Samsung"));
            brandRepo.save(new Brand("Apple"));
            brandRepo.save(new Brand("Samsung"));
        };
    }
}
