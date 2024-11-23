package io.github.bhavuklabs.sleepnswipebackend.commons.controllers;

import io.github.bhavuklabs.sleepnswipebackend.commons.mappers.GenericMapper;
import io.github.bhavuklabs.sleepnswipebackend.commons.persistence.GenericPersistenceService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class GenericCrudController<Entity, DTO, ID> {
    private static final Logger logger = LoggerFactory.getLogger(GenericCrudController.class);


    private final GenericPersistenceService<Entity, DTO,ID> service;
    private final GenericMapper<Entity, DTO> mapper;

    public GenericCrudController(GenericPersistenceService<Entity, DTO, ID> service, GenericMapper<Entity, DTO> mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<DTO>> getAll(Authentication authentication) {
        logger.debug("Fetching all entities");
        try {
            List<Entity> entities = this.service.getAll();
            List<DTO> dtos = entities.stream()
                    .map(mapper::toDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch(AccessDeniedException e) {
            logger.warn("Access Denied while Fetching all the Entities: {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DTO> getById(@PathVariable ID id, Authentication authentication) {
        logger.debug("Fetching entity by ID: {}", id);
        try {
            Optional<Entity> entity = this.service.getById(id);
            return entity.map(mapper::toDto)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch(AccessDeniedException e) {
            logger.warn("Access Denied while Fetching entity by ID: {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PostMapping
    public ResponseEntity<DTO> create(@RequestBody DTO dto, Authentication authentication) {
        logger.debug("Creating entity: {}", dto);
        try {
            Entity entity = this.mapper.fromDto(dto);
            var savedEntity = this.service.save(entity);
            DTO savedDto = this.mapper.toDto(savedEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedDto);
        } catch(AccessDeniedException e) {
            logger.warn("Access Denied while Creating entity: {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DTO> update(@PathVariable ID id, @RequestBody DTO dto, Authentication authentication) {
        logger.debug("Updating entity: {}", dto);
        try {
            Entity entity = this.mapper.fromDto(dto);
            Optional<Entity> updatedEntity = this.service.update(id, entity);
            return updatedEntity.map(this.mapper::toDto)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch(AccessDeniedException e) {
            logger.warn("Access Denied while Updating entity: {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable ID id, Authentication authentication) {
        logger.debug("Deleting entity: {}", id);
        try {
            if(this.service.delete(id))
                return ResponseEntity.noContent().build();
            else return ResponseEntity.notFound().build();
        } catch(AccessDeniedException e) {
            logger.warn("Access Denied while Deleting entity: {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        logger.error("Validation Error: {}", ex.getMessage());
        BindingResult result = ex.getBindingResult();
        Map<String, String> errors = new HashMap<>();
        for(FieldError error : result.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ae) {
        logger.warn("Access Denied: {}", ae.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ae.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        logger.error("Exception: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An unexpected error occured. Please try again later!");
    }
}
