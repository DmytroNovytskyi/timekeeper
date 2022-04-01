package com.epam.timekeeper.service.mapper;

import com.epam.timekeeper.dto.CategoryDTO;
import com.epam.timekeeper.entity.Category;
import com.epam.timekeeper.exception.DTOConversionException;

import java.util.HashMap;

/**
 * Maps Category with specified locale data transfer object to pass between service and servlet layers.
 */
public class CategoryDTOMapper {

    private CategoryDTOMapper() {
    }

    public static CategoryDTO toDTO(Category entity, String lang) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(entity.getId());
        String name = entity.getLangName().get(lang);
        if(name == null || name.isEmpty()){
            name = entity.getLangName().get("en");
            if(name == null || name.isEmpty()) {
                throw new DTOConversionException("could not find category name for lang=" + lang + " or default for lang=en");
            } else {
                dto.setName(name);
            }
        } else {
            dto.setName(name);
        }
        dto.setStatus(entity.getStatus());
        return dto;
    }

    public static Category toEntity(CategoryDTO dto, String lang) {
        Category entity = new Category();
        entity.setId(dto.getId());
        HashMap<String, String> map = new HashMap<>();
        map.put(lang, dto.getName());
        entity.setLangName(map);
        entity.setStatus(dto.getStatus());
        return entity;
    }

}
