package com.epam.timekeeper.dto;

import java.util.Objects;

/**
 * Data transfer object for Role. Used to transfer
 * data from servlet layer to service layer and vise versa.
 */
public class RoleDTO extends DTO{

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
        if (!(o instanceof RoleDTO roleDTO)) return false;
        return name.equals(roleDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return String.format("| %3d | %10s |",
                getId(), name);
    }

}
