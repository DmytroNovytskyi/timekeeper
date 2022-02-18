package com.epam.timekeeper.dto;

import com.epam.timekeeper.entity.Activity;

import java.util.Objects;

public class ActivityDTO extends DTO {

    private CategoryDTO category;
    private String name;
    Activity.Status status;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActivityDTO that)) return false;
        return category.equals(that.category) && name.equals(that.name) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, name, status);
    }

    @Override
    public String toString() {
        return String.format("| %3d | %10s | %15s | %6s |",
                getId(), category, name, status.name());
    }

}
