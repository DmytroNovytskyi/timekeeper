package com.epam.timekeeper.entity;

import java.util.Objects;

/**
 * Entity for User. Used to transfer data from service
 * layer to DAO layer and vise versa.
 */
public class User extends Entity {
    private String username;
    private int roleId;
    private String email;
    private String password;
    private Status status;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        if (!(o instanceof User user)) return false;
        return roleId == user.roleId && username.equals(user.username) && Objects.equals(email, user.email) && password.equals(user.password) && status == user.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, roleId, email, password, status);
    }

    @Override
    public String toString() {
        return String.format("| %3d | %15s | %3s | %20s | %15s | %6s |",
                getId(), username, roleId, email, password, status.name());
    }

    public enum Status {
        ACTIVE, BANNED
    }

}
