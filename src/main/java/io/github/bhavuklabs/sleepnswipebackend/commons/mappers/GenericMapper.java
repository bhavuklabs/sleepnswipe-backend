package io.github.bhavuklabs.sleepnswipebackend.commons.mappers;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A generic mapper class that provides common mapping functionality
 * between entity objects and Data Transfer Objects (DTOs).
 *
 * @param <T> The entity type.
 * @param <D> The DTO (Data Transfer Object) type.
 */
@Component
public abstract class GenericMapper<T, D> {
    private final ModelMapper modelMapper;

    /**
     * Constructs a GenericMapper and configures the ModelMapper instance.
     *
     * @param modelMapper The ModelMapper instance to be used for mapping.
     */
    @Autowired
    public GenericMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
    }

    /**
     * Converts an entity to its corresponding DTO.
     *
     * @param entity The entity object to convert.
     * @return The DTO representation of the entity.
     */
    public D toDto(T entity) {
        return modelMapper.map(entity, getDtoClass());
    }

    /**
     * Converts a list of entities to a list of corresponding DTOs.
     *
     * @param entities The list of entity objects to convert.
     * @return A list of DTOs representing the entities.
     */
    public List<D> toDtoList(List<T> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Converts a DTO to its corresponding entity.
     *
     * @param dto The DTO object to convert.
     * @return The entity representation of the DTO.
     */
    public T fromDto(D dto) {
        return modelMapper.map(dto, getEntityClass());
    }

    /**
     * Updates an existing entity with values from another entity.
     *
     * @param existing The existing entity that needs to be updated.
     * @param updated The new entity containing updated values.
     * @return The updated entity with the new values applied.
     */
    public T updateEntity(T existing, T updated) {
        modelMapper.map(updated, existing);
        return existing;
    }

    /**
     * Returns the class type of the entity.
     * Implementations must provide the actual class.
     *
     * @return The entity class type.
     */
    protected abstract Class<T> getEntityClass();

    /**
     * Returns the class type of the DTO.
     * Implementations must provide the actual class.
     *
     * @return The DTO class type.
     */
    protected abstract Class<D> getDtoClass();
}