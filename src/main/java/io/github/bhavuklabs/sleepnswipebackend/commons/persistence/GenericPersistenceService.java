package io.github.bhavuklabs.sleepnswipebackend.commons.persistence;

import io.github.bhavuklabs.sleepnswipebackend.commons.mappers.GenericMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class GenericPersistenceService<Entity, ID> {

    private final JpaRepository<Entity, ID> repository;
    private final GenericMapper<Entity, ?> mapper;

    public GenericPersistenceService(
            JpaRepository<Entity, ID> repository,
            GenericMapper<Entity, ?> mapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
    }


    @Transactional(readOnly = true)
    public List<Entity> getAll() {
        return this.repository.findAll();
    }

    @Transactional(readOnly = true)
    public List<?> getAllDto() {
        return this.mapper.toDtoList(this.repository.findAll());
    }


    @Transactional(readOnly = true)
    public Optional<Entity> getById(ID id) {
        return this.repository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<?> getByIdDto(ID id) {
        return this.repository.findById(id).map(this.mapper::toDto);
    }


    @Transactional
    public Entity save(Entity entity) {
        return this.repository.save(entity);
    }

    @Transactional
    public Optional<Entity> update(ID id, Entity entity) {
        return this.repository.findById(id)
                .map(existing -> {
                    Entity updated = this.mapper.updateEntity(existing, entity);
                    return this.repository.save(updated);
                });
    }

    @Transactional
    public boolean delete(ID id) {
        if(repository.existsById(id)) {
            this.repository.deleteById(id);
            return true;
        }
        return false;
    }
}
