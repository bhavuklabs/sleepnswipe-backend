package io.github.bhavuklabs.sleepnswipebackend.commons.mappers;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public abstract class GenericMapper<T, D> {
    private final ModelMapper modelMapper;

    @Autowired
    public GenericMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
    }

    public D toDto(T entity) {
        return modelMapper.map(entity, getDtoClass());
    }

    public List<D> toDtoList(List<T> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public T fromDto(D dto) {
        return modelMapper.map(dto, getEntityClass());
    }

    public T updateEntity(T existing, T updated) {
        modelMapper.map(updated, existing);
        return existing;
    }

    protected abstract Class<T> getEntityClass();

    protected abstract Class<D> getDtoClass();
}