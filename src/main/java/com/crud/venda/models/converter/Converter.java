package com.crud.venda.models.converter;

import java.util.List;

public interface Converter<E, D> {

    E toEntity(D domain);
    D toDomain(E entity);

    default List<D> toDomains(List<E> entities){
        return entities.stream().map(this::toDomain).toList();
    }

    default List<E> toEntities(List<D> domains){
        return domains.stream().map(this::toEntity).toList();
    }
}
