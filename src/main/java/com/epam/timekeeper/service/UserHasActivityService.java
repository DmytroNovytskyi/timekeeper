package com.epam.timekeeper.service;

import com.epam.timekeeper.dao.impl.UserHasActivityDAOImpl;
import com.epam.timekeeper.dto.ActivityDTO;
import com.epam.timekeeper.dto.UserDTO;
import com.epam.timekeeper.dto.UserHasActivityDTO;
import com.epam.timekeeper.entity.UserHasActivity;
import com.epam.timekeeper.exception.DBException;
import com.epam.timekeeper.service.mapper.UserHasActivityDTOMapper;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents the part of service layer that works with userHasActivities.
 */
public class UserHasActivityService {

    private final UserHasActivityDAOImpl userHasActivityDAO = new UserHasActivityDAOImpl();

    public List<UserHasActivityDTO> getAll(String lang) {
        List<UserHasActivity> userHasActivities = userHasActivityDAO.readAll();
        if (userHasActivities == null) {
            return null;
        }
        return userHasActivities.stream()
                .map(uha -> UserHasActivityDTOMapper.toDTO(uha, lang))
                .collect(Collectors.toList());
    }

    public List<UserHasActivityDTO> getAllForUser(UserDTO user, String lang) {
        List<UserHasActivity> userHasActivities = userHasActivityDAO.readAll();
        if (userHasActivities == null) {
            return null;
        }
        int id = user.getId();
        return userHasActivities.stream()
                .filter(uha -> uha.getUserId() == id)
                .map(uha -> UserHasActivityDTOMapper.toDTO(uha, lang))
                .collect(Collectors.toList());
    }

    public List<UserHasActivityDTO> getActiveForUser(UserDTO user, String lang) {
        List<UserHasActivityDTO> userHasActivities = getAllForUser(user, lang);
        if (userHasActivities == null) {
            return null;
        }
        return userHasActivities.stream()
                .filter(uha -> (uha.getStatus().equals(UserHasActivity.Status.ASSIGNED)
                        || uha.getStatus().equals(UserHasActivity.Status.IN_PROGRESS)))
                .collect(Collectors.toList());
    }

    public List<UserHasActivityDTO> getAllPending(String lang) {
        List<UserHasActivity> userHasActivities = userHasActivityDAO.readAll();
        if (userHasActivities == null) {
            return null;
        }
        return userHasActivities.stream()
                .filter(uha -> uha.getStatus().equals(UserHasActivity.Status.PENDING_ASSIGN)
                        || uha.getStatus().equals(UserHasActivity.Status.PENDING_ABORT))
                .map(uha -> UserHasActivityDTOMapper.toDTO(uha, lang))
                .collect(Collectors.toList());
    }

    public List<UserHasActivityDTO> getPendingForUser(UserDTO user, String lang) {
        List<UserHasActivity> userHasActivities = userHasActivityDAO.readAll();
        if (userHasActivities == null) {
            return null;
        }
        return userHasActivities.stream()
                .filter(uha -> uha.getUserId() == user.getId()
                        && (uha.getStatus().equals(UserHasActivity.Status.PENDING_ASSIGN)
                        || uha.getStatus().equals(UserHasActivity.Status.PENDING_ABORT)))
                .map(uha -> UserHasActivityDTOMapper.toDTO(uha, lang))
                .collect(Collectors.toList());
    }

    public void requestActivity(UserHasActivityDTO userHasActivity) {
        userHasActivityDAO.create(UserHasActivityDTOMapper.toEntity(userHasActivity));
    }

    public void start(UserHasActivityDTO userHasActivity) {
        userHasActivity.setStatus(UserHasActivity.Status.IN_PROGRESS);
        userHasActivity.setStartTime(new Timestamp(System.currentTimeMillis()));
        userHasActivityDAO.update(UserHasActivityDTOMapper.toEntity(userHasActivity));
    }

    public void end(UserHasActivityDTO userHasActivity) {
        userHasActivity.setStatus(UserHasActivity.Status.COMPLETED);
        userHasActivity.setStartTime(readStartTimeFromDB(userHasActivity.getId()));
        userHasActivity.setEndTime(new Timestamp(System.currentTimeMillis()));
        userHasActivityDAO.update(UserHasActivityDTOMapper.toEntity(userHasActivity));
    }

    public void requestAbort(UserHasActivityDTO userHasActivity) {
        userHasActivity.setStatus(UserHasActivity.Status.PENDING_ABORT);
        userHasActivity.setStartTime(readStartTimeFromDB(userHasActivity.getId()));
        userHasActivityDAO.update(UserHasActivityDTOMapper.toEntity(userHasActivity));
    }

    public void approveAssign(UserHasActivityDTO userHasActivity) {
        userHasActivity.setStatus(UserHasActivity.Status.ASSIGNED);
        userHasActivityDAO.update(UserHasActivityDTOMapper.toEntity(userHasActivity));
    }

    public void declineAssign(UserHasActivityDTO userHasActivity) {
        userHasActivity.setStatus(UserHasActivity.Status.DECLINED);
        userHasActivityDAO.update(UserHasActivityDTOMapper.toEntity(userHasActivity));
    }

    public void approveAbort(UserHasActivityDTO userHasActivity) {
        userHasActivity.setStatus(UserHasActivity.Status.ABORTED);
        userHasActivity.setStartTime(readStartTimeFromDB(userHasActivity.getId()));
        userHasActivityDAO.update(UserHasActivityDTOMapper.toEntity(userHasActivity));
    }

    public void declineAbort(UserHasActivityDTO userHasActivity) {
        userHasActivity.setStatus(UserHasActivity.Status.IN_PROGRESS);
        userHasActivity.setStartTime(readStartTimeFromDB(userHasActivity.getId()));
        userHasActivityDAO.update(UserHasActivityDTOMapper.toEntity(userHasActivity));
    }

    public void cancelAssign(UserHasActivityDTO userHasActivity) {
        userHasActivity.setStatus(UserHasActivity.Status.DECLINED);
        userHasActivityDAO.update(UserHasActivityDTOMapper.toEntity(userHasActivity));
    }

    public void cancelAbort(UserHasActivityDTO userHasActivity) {
        UserHasActivity entity = userHasActivityDAO.readById(userHasActivity.getId());
        if (entity == null) {
            throw new DBException("Couldn't find userHasActivity with id = " + userHasActivity.getId() + " in database.");
        } else if (entity.getStartTime() != null) {
            entity.setStatus(UserHasActivity.Status.IN_PROGRESS);
        } else {
            entity.setStatus(UserHasActivity.Status.ASSIGNED);
        }
        userHasActivityDAO.update(entity);
    }

    public List<ActivityDTO> mapToActivities(List<UserHasActivityDTO> list) {
        if (list == null) {
            return null;
        }
        return list.stream().map(UserHasActivityDTO::getActivity).collect(Collectors.toList());
    }

    public List<UserHasActivityDTO> getAllWithSummary(String lang) {
        List<UserHasActivity> userHasActivities = userHasActivityDAO.readAll();
        if (userHasActivities == null) {
            return null;
        }
        Map<Map.Entry<UserDTO, ActivityDTO>, List<UserHasActivityDTO>> map = userHasActivities.stream()
                .map(uha -> UserHasActivityDTOMapper.toDTO(uha, lang))
                .collect(Collectors.groupingBy(x -> new AbstractMap.SimpleEntry<>(x.getUser(), x.getActivity())));
        return map.entrySet().stream().collect(ArrayList::new, (list, x) -> {
            UserHasActivityDTO dto = new UserHasActivityDTO();
            long sum = 0;
            boolean hasCompleted = false;
            for (UserHasActivityDTO t :
                    x.getValue()) {
                if (t.getStatus().equals(UserHasActivity.Status.COMPLETED)) {
                    sum += t.getEndTime().getTime() - t.getStartTime().getTime();
                    hasCompleted = true;
                }
            }
            if (hasCompleted) {
                dto.setUser(x.getKey().getKey());
                dto.setActivity(x.getKey().getValue());
                Duration duration = Duration.ofMillis(sum);
                dto.setTimeSpent(String.format("%02d:%02d:%02d",
                        duration.toHours(), duration.toMinutesPart(), duration.toSecondsPart()));
                list.add(dto);
            }
        }, ArrayList::addAll);
    }

    private Timestamp readStartTimeFromDB(int id) {
        UserHasActivity dbInfo = userHasActivityDAO.readById(id);
        if (dbInfo == null) {
            throw new DBException("Couldn't find start time in database for UserHasActivity with id = " + id);
        }
        return dbInfo.getStartTime();
    }

}
