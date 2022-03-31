package com.epam.timekeeper.service.mapper;

import com.epam.timekeeper.dto.FullCategoryDTO;
import com.epam.timekeeper.entity.Category;

public class FullCategoryDTOMapper {

    private FullCategoryDTOMapper() {
    }

    public static FullCategoryDTO toDTO(Category entity) {
        FullCategoryDTO dto = new FullCategoryDTO();
        dto.setId(entity.getId());
        dto.setLangName(entity.getLangName());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    public static Category toEntity(FullCategoryDTO dto) {
        Category entity = new Category();
        entity.setId(dto.getId());
        entity.setLangName(dto.getLangName());
        entity.setStatus(dto.getStatus());
        return entity;
    }
}
