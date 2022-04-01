package com.epam.timekeeper.entity;

import java.util.HashMap;
import java.util.Objects;

/**
 * Entity for Category. Used to transfer data from service
 * layer to DAO layer and vise versa.
 */
public class Category extends Entity {

    private HashMap<String, String> langName;
    private Status status;

    public HashMap<String, String> getLangName() {
        return langName;
    }

    public void setLangName(HashMap<String, String> langName) {
        this.langName = langName;
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
        return langName.equals(category.langName) && status == category.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(langName, status);
    }

    @Override
    public String toString() {
        return String.format("| %3d | %15s | %7s |",
                getId(), langName, status.name());
    }

    public enum Status {
        OPENED, CLOSED
    }

}
