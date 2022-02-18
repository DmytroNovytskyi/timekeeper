package com.epam.timekeeper.entity;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Objects;

public class UserHasActivity extends Entity {

    private int userId;
    private int activityId;
    private Status status;
    private Timestamp startTime;
    private Time timeSpent;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
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
        if (!(o instanceof UserHasActivity that)) return false;
        return userId == that.userId && activityId == that.activityId && status == that.status && Objects.equals(startTime, that.startTime) && Objects.equals(timeSpent, that.timeSpent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, activityId, status, startTime, timeSpent);
    }

    @Override
    public String toString() {
        return String.format("| %3d | %3d | %3d | %10s | %tY-%tm-%td %tH:%tM:%tS |" +
                        " %tY-%tm-%td %tH:%tM:%tS |",
                getId(), userId, activityId, status.name(),
                startTime, startTime, startTime,
                startTime, startTime, startTime,
                timeSpent, timeSpent, timeSpent,
                timeSpent, timeSpent, timeSpent);
    }

    public enum Status {
        PENDING_ASSIGN, PENDING_ABORT, ASSIGNED, DECLINED, IN_PROGRESS, COMPLETED, ABORTED
    }

}
