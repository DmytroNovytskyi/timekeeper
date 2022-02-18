package com.epam.timekeeper.dto;

import com.epam.timekeeper.entity.User;

import java.util.Objects;

public class UserDTO extends DTO {

    private String username;
    private RoleDTO role;
    private String email;
    private User.Status status;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User.Status getStatus() {
        return status;
    }

    public void setStatus(User.Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO userDTO)) return false;
        return username.equals(userDTO.username) && role.equals(userDTO.role) && Objects.equals(email, userDTO.email) && status == userDTO.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, role, email, status);
    }

    @Override
    public String toString() {
        return String.format("| %3d | %15s | %8s | %20s | %6s |",
                getId(), username, role.getName(), email, status.name());
    }
}
