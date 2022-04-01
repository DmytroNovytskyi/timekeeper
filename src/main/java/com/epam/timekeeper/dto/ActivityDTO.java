package com.epam.timekeeper.dto;

import com.epam.timekeeper.entity.Activity;

import java.util.Objects;

/**
 * Data transfer object for Activity. Used to transfer
 * data from servlet layer to service layer and vise versa.
 */
public class ActivityDTO extends DTO {

    private CategoryDTO category;
    private String name;
    private Activity.Status status;
    private int userCount;
    private String description;

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Activity.Status getStatus() {
        return status;
    }

    public void setStatus(Activity.Status status) {
        this.status = status;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
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
        if (!(o instanceof ActivityDTO that)) return false;
        return userCount == that.userCount && category.equals(that.category) && name.equals(that.name) && status == that.status && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, name, status, userCount, description);
    }

    @Override
    public String toString() {
        return String.format("| %3d | %10s | %15s | %6s | %3d | %s |",
                getId(), category, name, status.name(), userCount,
                description != null && !description.isEmpty());
    }

}
