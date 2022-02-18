package com.epam.timekeeper.entity;

import java.util.Objects;

public class Category extends Entity {

    private String name;
    private Status status;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category category)) return false;
        return name.equals(category.name) && status == category.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, status);
    }

    @Override
    public String toString() {
        return String.format("| %3d | %10s | %7s |",
                getId(), name, status.name());
    }

    public enum Status {
        OPENED, CLOSED
    }

}
