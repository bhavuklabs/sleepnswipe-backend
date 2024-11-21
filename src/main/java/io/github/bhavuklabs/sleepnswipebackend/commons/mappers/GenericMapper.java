package io.github.bhavuklabs.sleepnswipebackend.commons.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

public interface GenericMapper<Entity, Dto> {

    Dto toDto(Entity entity);

    List<Dto> toDtoList(List<Entity> entities);

    Entity toEntity(Dto dto);

    List<Entity> toEntityList(List<Dto> dtos);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Entity updateEntity(Entity entity, @MappingTarget Entity updatedEntity);
}
