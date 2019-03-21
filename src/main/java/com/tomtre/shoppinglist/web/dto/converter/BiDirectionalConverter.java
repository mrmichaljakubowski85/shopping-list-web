package com.tomtre.shoppinglist.web.dto.converter;

import com.tomtre.shoppinglist.web.entity.BaseIdEntity;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public interface BiDirectionalConverter<E extends BaseIdEntity, D> {

    D convertToDto(E from);
    E convertToEntity(D from);

    default List<D> convertAllToDto(Collection<E> elements){
        return elements.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    default List<E> convertAllToEntities(Collection<D> elements){
        return elements.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
    }


}
