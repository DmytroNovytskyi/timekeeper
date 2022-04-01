package com.epam.timekeeper.service;

import com.epam.timekeeper.dao.impl.ActivityDAOImpl;
import com.epam.timekeeper.dto.UserDTO;
import com.epam.timekeeper.dto.UserHasActivityDTO;
import com.epam.timekeeper.entity.Activity;
import com.epam.timekeeper.dto.ActivityDTO;
import com.epam.timekeeper.exception.ObjectNotFoundException;
import com.epam.timekeeper.service.mapper.ActivityDTOMapper;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents the part of service layer that works with activities.
 */
public class ActivityService {

    private final ActivityDAOImpl activityDAO = new ActivityDAOImpl();

    public List<ActivityDTO> getAll(String lang) {
        List<Activity> activities = activityDAO.readAll();
        if (activities == null) {
            return null;
        }
        return activities.stream()
                .map(a -> ActivityDTOMapper.toDTO(a, lang))
                .sorted(Comparator
                        .comparing((ActivityDTO a) -> a.getCategory().getName())
                        .thenComparing(ActivityDTO::getName))
                .collect(Collectors.toList());
    }

    public List<ActivityDTO> getAllOpened(String lang) {
        List<Activity> activities = activityDAO.readAll();
        if (activities == null) {
            return null;
        }
        return activities.stream()
                .filter(a -> a.getStatus().equals(Activity.Status.OPENED))
                .map(a -> ActivityDTOMapper.toDTO(a, lang))
                .sorted(Comparator
                        .comparing((ActivityDTO a) -> a.getCategory().getName())
                        .thenComparing(ActivityDTO::getName))
                .collect(Collectors.toList());
    }

    public List<ActivityDTO> getFreeForUser(UserDTO user, String lang) {
        List<ActivityDTO> allOpenedActivities = getAllOpened(lang);
        UserHasActivityService userHasActivityService = new UserHasActivityService();
        List<UserHasActivityDTO> activeUHAForUser = userHasActivityService.getActiveForUser(user, lang);
        List<UserHasActivityDTO> pendingUHAForUser = userHasActivityService.getPendingForUser(user, lang);
        if (activeUHAForUser != null) {
            allOpenedActivities.removeAll(userHasActivityService.mapToActivities(activeUHAForUser));
        }
        if (pendingUHAForUser != null) {
            allOpenedActivities.removeAll(userHasActivityService.mapToActivities(pendingUHAForUser));
        }
        return allOpenedActivities;
    }

    public void create(ActivityDTO activity) {
        activityDAO.create(ActivityDTOMapper.toEntity(activity));
    }

    public void update(ActivityDTO activity) {
        Activity entity = activityDAO.readById(activity.getId());
        if (entity == null) {
            throw new ObjectNotFoundException("Couldn't find activity with id = " + activity.getId() + " in database.");
        }
        entity.setCategoryID(activity.getCategory().getId());
        entity.setName(activity.getName());
        entity.setDescription(activity.getDescription());
        activityDAO.update(entity);
    }

    public void close(ActivityDTO activity) {
        Activity entity = activityDAO.readById(activity.getId());
        if (entity == null) {
            throw new ObjectNotFoundException("Couldn't find activity with id = " + activity.getId() + " in database.");
        }
        entity.setStatus(Activity.Status.CLOSED);
        activityDAO.update(entity);
    }

    public void open(ActivityDTO activity) {
        Activity entity = activityDAO.readById(activity.getId());
        if (entity == null) {
            throw new ObjectNotFoundException("Couldn't find activity with id = " + activity.getId() + " in database.");
        }
        entity.setStatus(Activity.Status.OPENED);
        activityDAO.update(entity);
    }

}
