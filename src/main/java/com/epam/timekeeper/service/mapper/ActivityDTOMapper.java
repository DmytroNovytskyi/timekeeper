package com.epam.timekeeper.service.mapper;

import com.epam.timekeeper.dao.impl.CategoryDAOImpl;
import com.epam.timekeeper.dao.impl.UserHasActivityDAOImpl;
import com.epam.timekeeper.dto.ActivityDTO;
import com.epam.timekeeper.dto.CategoryDTO;
import com.epam.timekeeper.entity.Activity;
import com.epam.timekeeper.entity.Category;
import com.epam.timekeeper.entity.UserHasActivity;
import com.epam.timekeeper.exception.DTOConversionException;

import java.util.List;

/**
 * Maps Activity data transfer object to pass between service and servlet layers.
 */
public class ActivityDTOMapper {

    private ActivityDTOMapper() {
    }

    public static ActivityDTO toDTO(Activity entity, String lang) {
        ActivityDTO dto = new ActivityDTO();
        CategoryDAOImpl categoryDAO = new CategoryDAOImpl();
        Category category = categoryDAO.readById(entity.getCategoryID());
        if (category == null) {
            throw new DTOConversionException("Error occurred while creating ActivityDTO");
        }
        dto.setId(entity.getId());
        dto.setCategory(CategoryDTOMapper.toDTO(category, lang));
        dto.setName(entity.getName());
        dto.setStatus(entity.getStatus());
        dto.setDescription(entity.getDescription());
        UserHasActivityDAOImpl userHasActivityDAO = new UserHasActivityDAOImpl();
        List<UserHasActivity> userHasActivities = userHasActivityDAO.readAll();
        if (userHasActivities == null) {
            throw new DTOConversionException("Error occurred while creating ActivityDTO");
        }
        dto.setUserCount((int) userHasActivities.stream()
                .filter(uha -> uha.getActivityId() == dto.getId()
                        && (uha.getStatus().equals(UserHasActivity.Status.IN_PROGRESS)
                        || uha.getStatus().equals(UserHasActivity.Status.ASSIGNED))).count());
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
        entity.setDescription(dto.getDescription());
        return entity;
    }

}
