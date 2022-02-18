package com.epam.timekeeper.service.mapper;

import com.epam.timekeeper.dto.CategoryDTO;
import com.epam.timekeeper.entity.Category;

public class CategoryDTOMapper {

    private CategoryDTOMapper() {
    }

    public static CategoryDTO toDTO(Category entity) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    public static Category toEntity(CategoryDTO dto) {
        Category entity = new Category();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setStatus(dto.getStatus());
        return entity;
    }

}
