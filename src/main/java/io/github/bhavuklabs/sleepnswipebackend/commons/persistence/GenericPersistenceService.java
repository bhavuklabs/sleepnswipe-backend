package io.github.bhavuklabs.sleepnswipebackend.commons.persistence;

import io.github.bhavuklabs.sleepnswipebackend.commons.mappers.GenericMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Generic service class providing CRUD operations using Spring Data JPA.
 *
 * @param <Entity> The entity type representing the database model.
 * @param <DTO> The Data Transfer Object (DTO) type corresponding to the entity.
 * @param <ID> The type of the primary key (ID) for the entity.
 */
public class GenericPersistenceService<Entity, DTO, ID> {

    private final JpaRepository<Entity, ID> repository;
    private final GenericMapper<Entity, DTO> mapper;

    /**
     * Constructor to initialize the persistence service with a repository and mapper.
     *
     * @param repository The JPA repository for entity persistence.
     * @param mapper The mapper for converting between Entity and DTO.
     */
    public GenericPersistenceService(
            JpaRepository<Entity, ID> repository,
            GenericMapper<Entity, DTO> mapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Retrieves all entities from the database.
     *
     * @return A list of all entities.
     */
    @Transactional(readOnly = true)
    public List<Entity> getAll() {
        return this.repository.findAll();
    }

    /**
     * Retrieves all entities from the database and converts them to DTOs.
     *
     * @return A list of DTO representations of the entities.
     */
    @Transactional(readOnly = true)
    public List<?> getAllDto() {
        return this.mapper.toDtoList(this.repository.findAll());
    }

    /**
     * Retrieves an entity by its ID.
     *
     * @param id The ID of the entity to retrieve.
     * @return An Optional containing the entity if found, otherwise empty.
     */
    @Transactional(readOnly = true)
    public Optional<Entity> getById(ID id) {
        return this.repository.findById(id);
    }

    /**
     * Retrieves an entity by its ID and converts it to a DTO.
     *
     * @param id The ID of the entity to retrieve.
     * @return An Optional containing the DTO if the entity is found, otherwise empty.
     */
    @Transactional(readOnly = true)
    public Optional<?> getByIdDto(ID id) {
        return this.repository.findById(id).map(this.mapper::toDto);
    }

    /**
     * Saves a new entity to the database.
     *
     * @param entity The entity to be saved.
     * @return The saved entity.
     */
    @Transactional
    public Entity save(Entity entity) {
        return this.repository.save(entity);
    }

    /**
     * Updates an existing entity if found.
     *
     * @param id The ID of the entity to update.
     * @param entity The updated entity details.
     * @return An Optional containing the updated entity if it exists, otherwise empty.
     */
    @Transactional
    public Optional<Entity> update(ID id, Entity entity) {
        return this.repository.findById(id)
                .map(existing -> {
                    Entity updated = this.mapper.updateEntity(existing, entity);
                    return this.repository.save(updated);
                });
    }

    /**
     * Deletes an entity by its ID.
     *
     * @param id The ID of the entity to delete.
     * @return true if the entity was deleted successfully, false if the entity was not found.
     */
    @Transactional
    public boolean delete(ID id) {
        if(repository.existsById(id)) {
            this.repository.deleteById(id);
            return true;
        }
        return false;
    }
}
