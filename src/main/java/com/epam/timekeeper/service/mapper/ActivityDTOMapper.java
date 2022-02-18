package com.epam.timekeeper.service.mapper;

import com.epam.timekeeper.dao.DAO;
import com.epam.timekeeper.dao.mapper.CategoryMapper;
import com.epam.timekeeper.dao.preparer.CategoryPreparer;
import com.epam.timekeeper.dto.ActivityDTO;
import com.epam.timekeeper.dto.CategoryDTO;
import com.epam.timekeeper.entity.Activity;
import com.epam.timekeeper.entity.Category;
import com.epam.timekeeper.exception.DTOConversionException;

public class ActivityDTOMapper {

    private ActivityDTOMapper() {
    }

    public static ActivityDTO toDTO(Activity entity) {
        ActivityDTO dto = new ActivityDTO();
        DAO<Category> categoryDAO = new DAO<>(new CategoryPreparer(), new CategoryMapper());
        Category category = categoryDAO.readById(entity.getCategoryID());
        if (category == null) {
            throw new DTOConversionException("Error occurred while creating ActivityDTO");
        }
        dto.setId(entity.getId());
        dto.setCategory(CategoryDTOMapper.toDTO(category));
        dto.setName(entity.getName());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    public static Activity toEntity(ActivityDTO dto) {
        Activity entity = new Activity();
        entity.setId(dto.getId());
        CategoryDTO category = dto.getCategory();
        if (category != null) {
            entity.setCategoryID(dto.getCategory().getId());
        }
        entity.setName(dto.getName());
        entity.setStatus(dto.getStatus());
        return entity;
    }

}
