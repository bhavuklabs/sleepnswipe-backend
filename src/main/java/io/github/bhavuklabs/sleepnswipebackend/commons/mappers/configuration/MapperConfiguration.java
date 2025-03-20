package io.github.bhavuklabs.sleepnswipebackend.commons.mappers.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up ModelMapper as a Spring Bean.
 * <p>
 * This class ensures that a single instance of {@link ModelMapper} is created
 * and managed by the Spring container, allowing for dependency injection
 * wherever needed.
 * </p>
 */
@Configuration
public class MapperConfiguration {

    /**
     * Creates and provides a {@link ModelMapper} bean.
     * <p>
     * ModelMapper is used to simplify object mapping between different classes,
     * typically between entities and DTOs.
     * </p>
     *
     * @return a new instance of {@link ModelMapper}
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
