package com.epam.timekeeper.dto;

import com.epam.timekeeper.entity.UserHasActivity;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Objects;

public class UserHasActivityDTO extends DTO{

    private UserDTO user;
    private ActivityDTO activity;
    private UserHasActivity.Status status;
    private Timestamp startTime;
    private Time timeSpent;

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

    public Time getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(Time timeSpent) {
        this.timeSpent = timeSpent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserHasActivityDTO that)) return false;
        return user == that.user && activity == that.activity && status == that.status && Objects.equals(startTime, that.startTime) && Objects.equals(timeSpent, that.timeSpent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, activity, status, startTime, timeSpent);
    }

    @Override
    public String toString() {
        return String.format("| %3d | %15s | %15s | %10s | %tY-%tm-%td %tH:%tM:%tS |" +
                        " %tY-%tm-%td %tH:%tM:%tS |",
                getId(), user.getUsername(), activity.getName(), status.name(),
                startTime, startTime, startTime,
                startTime, startTime, startTime,
                timeSpent, timeSpent, timeSpent,
                timeSpent, timeSpent, timeSpent);


    }

}
