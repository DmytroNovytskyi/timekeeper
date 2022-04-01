package com.epam.timekeeper.entity;

import java.util.Objects;

/**
 * Entity for Role. Used to transfer data from service
 * layer to DAO layer and vise versa.
 */
public class Role extends Entity {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role role)) return false;
        return this.getId() == role.getId() && name.equals(role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), name);
    }

    @Override
    public String toString() {
        return String.format("| %3d | %10s |",
                getId(), name);
    }

}
