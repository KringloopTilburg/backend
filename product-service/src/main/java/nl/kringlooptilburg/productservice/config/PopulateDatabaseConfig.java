package nl.kringlooptilburg.productservice.config;

import java.util.HashSet;
import java.util.UUID;
import nl.kringlooptilburg.productservice.domain.entities.ColorEntity;
import nl.kringlooptilburg.productservice.domain.entities.enums.Color;
import nl.kringlooptilburg.productservice.repositories.ColorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PopulateDatabaseConfig {

    // Populate the database with the colors, adding a new color is currently not possible
    @Bean
    public CommandLineRunner populateColorRepository(ColorRepository colorRepository) {
        return args -> {
            if (colorRepository.count() == 0) {
                for (Color color : Color.values()) {
                    ColorEntity colorEntity = ColorEntity.builder()
                            .colorId(UUID.randomUUID())
                            .color(color.name())
                            .products(new HashSet<>())
                            .build();
                    colorRepository.save(colorEntity);
                }
            }
        };
    }
}
