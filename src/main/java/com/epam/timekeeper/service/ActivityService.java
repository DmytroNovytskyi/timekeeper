package com.epam.timekeeper.service;

import com.epam.timekeeper.dao.DAO;
import com.epam.timekeeper.dao.mapper.ActivityMapper;
import com.epam.timekeeper.dao.preparer.ActivityPreparer;
import com.epam.timekeeper.dto.UserDTO;
import com.epam.timekeeper.dto.UserHasActivityDTO;
import com.epam.timekeeper.entity.Activity;
import com.epam.timekeeper.dto.ActivityDTO;
import com.epam.timekeeper.entity.Category;
import com.epam.timekeeper.exception.ObjectNotFoundException;
import com.epam.timekeeper.service.mapper.ActivityDTOMapper;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ActivityService {

    private final DAO<Activity> activityDAO = new DAO<>(new ActivityPreparer(), new ActivityMapper());

    public List<ActivityDTO> getAll() {
        List<Activity> activities = activityDAO.readAll();
        if (activities == null) {
            return null;
        }
        return activities.stream()
                .map(ActivityDTOMapper::toDTO)
                .sorted(Comparator
                        .comparing((ActivityDTO a) -> a.getCategory().getName())
                        .thenComparing(ActivityDTO::getName))
                .collect(Collectors.toList());
    }

    public List<ActivityDTO> getAllOpened() {
        List<Activity> activities = activityDAO.readAll();
        if (activities == null) {
            return null;
        }
        return activities.stream()
                .filter(a -> a.getStatus().equals(Activity.Status.OPENED))
                .map(ActivityDTOMapper::toDTO)
                .sorted(Comparator
                        .comparing((ActivityDTO a) -> a.getCategory().getName())
                        .thenComparing(ActivityDTO::getName))
                .collect(Collectors.toList());
    }

    public List<ActivityDTO> getFreeForUser(UserDTO user) {
        List<ActivityDTO> allOpenedActivities = getAllOpened();
        UserHasActivityService userHasActivityService = new UserHasActivityService();
        List<UserHasActivityDTO> activeUHAForUser = userHasActivityService.getActiveForUser(user);
        List<UserHasActivityDTO> pendingUHAForUser = userHasActivityService.getPendingForUser(user);
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

    public ActivityDTO get(ActivityDTO dto) {
        Activity entity = activityDAO.readById(dto.getId());
        return entity == null ? null : ActivityDTOMapper.toDTO(entity);
    }

    public void update(ActivityDTO activity) {
        Activity entity = activityDAO.readById(activity.getId());
        if (entity == null) {
            throw new ObjectNotFoundException("Couldn't find activity with id = " + activity.getId() + " in database.");
        }
        entity.setCategoryID(activity.getCategory().getId());
        entity.setName(activity.getName());
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
