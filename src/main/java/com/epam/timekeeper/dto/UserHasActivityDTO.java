package com.epam.timekeeper.dto;

import com.epam.timekeeper.entity.UserHasActivity;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * Data transfer object for UserHasActivity. Used to transfer
 * data from servlet layer to service layer and vise versa.
 */
public class UserHasActivityDTO extends DTO {

    private UserDTO user;
    private ActivityDTO activity;
    private UserHasActivity.Status status;
    private Timestamp startTime;
    private Timestamp endTime;
    private Timestamp creationDate;
    private String timeSpent;

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public ActivityDTO getActivity() {
        return activity;
    }

    public void setActivity(ActivityDTO activity) {
        this.activity = activity;
    }

    public UserHasActivity.Status getStatus() {
        return status;
    }

    public void setStatus(UserHasActivity.Status status) {
        this.status = status;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(String timeSpent) {
        this.timeSpent = timeSpent;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserHasActivityDTO that)) return false;
        return user.equals(that.user) && activity.equals(that.activity) && status == that.status && Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime) && creationDate.equals(that.creationDate) && Objects.equals(timeSpent, that.timeSpent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, activity, status, startTime, endTime, creationDate, timeSpent);
    }

    @Override
    public String toString() {
        return String.format("| %3d | %15s | %15s | %14s | %s | %s | %s | %s |",
                getId(), user.getUsername(), activity.getName(), status.name(), startTime, endTime, timeSpent, creationDate);
    }

}
