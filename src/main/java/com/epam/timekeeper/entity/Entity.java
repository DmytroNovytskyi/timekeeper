package com.epam.timekeeper.entity;

/**
 * Abstract entity with id. Created to be extended by real entity.
 */
public abstract class Entity {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
