package com.epam.timekeeper.dto;

import com.epam.timekeeper.entity.Category;

import java.util.HashMap;
import java.util.Objects;

/**
 * Data transfer object for Category. Contains HashMap with all
 * entries with language and corresponding name of category. Used to transfer
 * data from servlet layer to service layer and vise versa.
 */
public class FullCategoryDTO extends DTO{

    private HashMap<String, String> langName;
    private Category.Status status;

    public HashMap<String, String> getLangName() {
        return langName;
    }

    public void setLangName(HashMap<String, String> langName) {
        this.langName = langName;
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
        if (!(o instanceof FullCategoryDTO that)) return false;
        return langName.equals(that.langName) && status == that.status;
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

}
