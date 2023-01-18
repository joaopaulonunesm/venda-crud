package com.crud.venda.models.converter;

import java.util.ArrayList;
import java.util.List;

public interface Converter<E, D> {

    E toEntity(D domain);
    D toDomain(E entity);

    default List<D> toDomains(List<E> entities){
        List<D> domains = new ArrayList<>();
        entities.stream().forEach(entity -> domains.add(toDomain(entity)));
        return domains;
    }

    default List<E> toEntities(List<D> domains){
        List<E> entities = new ArrayList<>();
        domains.stream().forEach(domain -> entities.add(toEntity(domain)));
        return entities;
    }
}
