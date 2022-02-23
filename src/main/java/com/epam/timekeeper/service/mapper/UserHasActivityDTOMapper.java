package com.epam.timekeeper.service.mapper;

import com.epam.timekeeper.dao.DAO;
import com.epam.timekeeper.dao.mapper.ActivityMapper;
import com.epam.timekeeper.dao.mapper.UserMapper;
import com.epam.timekeeper.dao.preparer.ActivityPreparer;
import com.epam.timekeeper.dao.preparer.UserPreparer;
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

    public static UserHasActivityDTO toDTO(UserHasActivity entity) {
        UserHasActivityDTO dto = new UserHasActivityDTO();
        dto.setId(entity.getId());
        DAO<User> userDAO = new DAO<>(new UserPreparer(), new UserMapper());
        DAO<Activity> activityDAO = new DAO<>(new ActivityPreparer(), new ActivityMapper());
        User user = userDAO.readById(entity.getUserId());
        Activity activity = activityDAO.readById(entity.getActivityId());
        if (user == null || activity == null) {
            throw new DTOConversionException("Error occurred while creating UserHasActivityDTO");
        }
        dto.setUser(UserDTOMapper.toDTO(user));
        dto.setActivity(ActivityDTOMapper.toDTO(activity));
        dto.setStatus(entity.getStatus());
        Timestamp start = entity.getStartTime();
        Timestamp end = entity.getEndTime();
        dto.setStartTime(start);
        dto.setEndTime(end);
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
        return entity;
    }
}
