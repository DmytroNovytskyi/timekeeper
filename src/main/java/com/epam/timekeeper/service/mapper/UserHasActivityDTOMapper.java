package com.epam.timekeeper.service.mapper;

import com.epam.timekeeper.dao.impl.ActivityDAOImpl;
import com.epam.timekeeper.dao.impl.UserDAOImpl;
import com.epam.timekeeper.dto.ActivityDTO;
import com.epam.timekeeper.dto.UserDTO;
import com.epam.timekeeper.dto.UserHasActivityDTO;
import com.epam.timekeeper.entity.Activity;
import com.epam.timekeeper.entity.User;
import com.epam.timekeeper.entity.UserHasActivity;
import com.epam.timekeeper.exception.DTOConversionException;

import java.sql.Timestamp;
import java.time.Duration;

public class UserHasActivityDTOMapper {

    private UserHasActivityDTOMapper() {
    }

    public static UserHasActivityDTO toDTO(UserHasActivity entity, String lang) {
        UserHasActivityDTO dto = new UserHasActivityDTO();
        dto.setId(entity.getId());
        UserDAOImpl userDAO = new UserDAOImpl();
        ActivityDAOImpl activityDAO = new ActivityDAOImpl();
        User user = userDAO.readById(entity.getUserId());
        Activity activity = activityDAO.readById(entity.getActivityId());
        if (user == null || activity == null) {
            throw new DTOConversionException("Error occurred while creating UserHasActivityDTO");
        }
        dto.setUser(UserDTOMapper.toDTO(user));
        dto.setActivity(ActivityDTOMapper.toDTO(activity, lang));
        dto.setStatus(entity.getStatus());
        Timestamp start = entity.getStartTime();
        Timestamp end = entity.getEndTime();
        dto.setStartTime(start);
        dto.setEndTime(end);
        dto.setCreationDate(entity.getCreationDate());
        if (start != null && end != null) {
            Duration duration = Duration.ofMillis(end.getTime() - start.getTime());
            dto.setTimeSpent(String.format("%02d:%02d:%02d",
                    duration.toHours(), duration.toMinutesPart(), duration.toSecondsPart()));
        }
        return dto;
    }

    public static UserHasActivity toEntity(UserHasActivityDTO dto) {
        UserHasActivity entity = new UserHasActivity();
        entity.setId(dto.getId());
        UserDTO user = dto.getUser();
        if (user != null) {
            entity.setUserId(user.getId());
        }
        ActivityDTO activity = dto.getActivity();
        if (activity != null) {
            entity.setActivityId(dto.getActivity().getId());
        }
        entity.setStatus(dto.getStatus());
        entity.setStartTime(dto.getStartTime());
        entity.setEndTime(dto.getEndTime());
        entity.setCreationDate(dto.getCreationDate());
        return entity;
    }
}
