package com.epam.timekeeper.dto;

import java.io.Serializable;

/**
 * Abstract data transfer object with id. Created to be
 * extended by real DTO.
 */
public abstract class DTO implements Serializable {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
