package io.github.bhavuklabs.sleepnswipebackend.commons.controllers;

import io.github.bhavuklabs.sleepnswipebackend.commons.mappers.GenericMapper;
import io.github.bhavuklabs.sleepnswipebackend.commons.persistence.GenericPersistenceService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * A generic CRUD controller to handle Create, Read, Update, and Delete operations.
 * This controller is meant to be extended by specific entity controllers.
 *
 * @param <Entity> The entity type.
 * @param <DTO> The DTO (Data Transfer Object) type.
 * @param <ID> The ID type of the entity (e.g., Long, String, UUID, etc.).
 */
public abstract class GenericCrudController<Entity, DTO, ID> {
    private static final Logger logger = LoggerFactory.getLogger(GenericCrudController.class);

    private final GenericPersistenceService<Entity, DTO, ID> service;
    private final GenericMapper<Entity, DTO> mapper;

    /**
     * Constructor to initialize the service and mapper.
     *
     * @param service The persistence service for CRUD operations.
     * @param mapper  The mapper to convert between Entity and DTO.
     */
    public GenericCrudController(GenericPersistenceService<Entity, DTO, ID> service, GenericMapper<Entity, DTO> mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    /**
     * Retrieves all entities.
     *
     * @param authentication The authentication object for security context.
     * @return A list of all DTOs wrapped in ResponseEntity.
     */
    @GetMapping
    public ResponseEntity<List<DTO>> getAll(Authentication authentication) {
        logger.debug("Fetching all entities");
        try {
            List<Entity> entities = this.service.getAll();
            List<DTO> dtos = entities.stream().map(mapper::toDto).collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (AccessDeniedException e) {
            logger.warn("Access Denied while Fetching all the Entities: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    /**
     * Retrieves an entity by its ID.
     *
     * @param id             The ID of the entity to retrieve.
     * @param authentication The authentication object for security context.
     * @return The DTO wrapped in ResponseEntity, or a 404 Not Found response if the entity is missing.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DTO> getById(@PathVariable ID id, Authentication authentication) {
        logger.debug("Fetching entity by ID: {}", id);
        try {
            Optional<Entity> entity = this.service.getById(id);
            return entity.map(mapper::toDto).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        } catch (AccessDeniedException e) {
            logger.warn("Access Denied while Fetching entity by ID: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    /**
     * Creates a new entity.
     *
     * @param dto            The DTO representing the entity to be created.
     * @param authentication The authentication object for security context.
     * @return The created entity's DTO wrapped in ResponseEntity.
     */
    @PostMapping
    public ResponseEntity<DTO> create(@RequestBody DTO dto, Authentication authentication) {
        logger.debug("Creating entity: {}", dto);
        try {
            Entity entity = this.mapper.fromDto(dto);
            var savedEntity = this.service.save(entity);
            DTO savedDto = this.mapper.toDto(savedEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedDto);
        } catch (AccessDeniedException e) {
            logger.warn("Access Denied while Creating entity: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    /**
     * Updates an existing entity.
     *
     * @param id             The ID of the entity to update.
     * @param dto            The DTO containing updated entity details.
     * @param authentication The authentication object for security context.
     * @return The updated entity DTO, or a 404 Not Found response if the entity does not exist.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DTO> update(@PathVariable ID id, @RequestBody DTO dto, Authentication authentication) {
        logger.debug("Updating entity: {}", dto);
        try {
            Entity entity = this.mapper.fromDto(dto);
            Optional<Entity> updatedEntity = this.service.update(id, entity);
            return updatedEntity.map(this.mapper::toDto).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        } catch (AccessDeniedException e) {
            logger.warn("Access Denied while Updating entity: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    /**
     * Deletes an entity by its ID.
     *
     * @param id             The ID of the entity to delete.
     * @param authentication The authentication object for security context.
     * @return A 204 No Content response if successful, or 404 Not Found if the entity does not exist.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable ID id, Authentication authentication) {
        logger.debug("Deleting entity: {}", id);
        try {
            if (this.service.delete(id))
                return ResponseEntity.noContent().build();
            else return ResponseEntity.notFound().build();
        } catch (AccessDeniedException e) {
            logger.warn("Access Denied while Deleting entity: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}