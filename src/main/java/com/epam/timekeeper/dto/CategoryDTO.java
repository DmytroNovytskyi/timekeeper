package com.epam.timekeeper.dto;

import com.epam.timekeeper.entity.Category;

import java.util.Objects;

public class CategoryDTO extends DTO{

    private String name;
    private Category.Status status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category.Status getStatus() {
        return status;
    }

    public void setStatus(Category.Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryDTO that)) return false;
        return name.equals(that.name) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, status);
    }

    @Override
    public String toString() {
        return String.format("| %3d | %10s | %7s |",
                getId(),  name, status.name());
    }

}
