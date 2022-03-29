package com.epam.timekeeper.entity;

import java.util.Objects;

public class Activity extends Entity {

    private int categoryID;
    private String name;
    private Status status;
    private String description;

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryId) {
        this.categoryID = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Activity activity)) return false;
        return categoryID == activity.categoryID && name.equals(activity.name) && status == activity.status && Objects.equals(description, activity.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryID, name, status, description);
    }

    @Override
    public String toString() {
        return String.format("| %3d | %3s | %20s | %6s | %s |",
                getId(), categoryID, name, status.name(), description == null);
    }

    public enum Status {
        OPENED, CLOSED
    }

}
