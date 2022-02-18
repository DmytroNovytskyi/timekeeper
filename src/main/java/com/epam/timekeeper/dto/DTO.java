package com.epam.timekeeper.dto;

import java.io.Serializable;

public abstract class DTO implements Serializable {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
