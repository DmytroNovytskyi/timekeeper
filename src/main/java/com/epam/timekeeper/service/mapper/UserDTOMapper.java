package com.epam.timekeeper.service.mapper;

import com.epam.timekeeper.dao.impl.RoleDAOImpl;
import com.epam.timekeeper.dao.impl.UserDAOImpl;
import com.epam.timekeeper.dto.RoleDTO;
import com.epam.timekeeper.dto.UserDTO;
import com.epam.timekeeper.entity.Role;
import com.epam.timekeeper.entity.User;
import com.epam.timekeeper.exception.DTOConversionException;

public class UserDTOMapper {

    private UserDTOMapper() {
    }

    public static UserDTO toDTO(User entity) {
        UserDTO dto = new UserDTO();
        RoleDAOImpl roleDAO = new RoleDAOImpl();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        Role role = roleDAO.readById(entity.getRoleId());
        if (role == null) {
            throw new DTOConversionException("Error occurred while creating UserDTO");
        }
        dto.setRole(RoleDTOMapper.toDTO(role));
        dto.setEmail(entity.getEmail());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    public static User toEntity(UserDTO dto, String password) {
        User entity = new User();
        entity.setId(dto.getId());
        entity.setUsername(dto.getUsername());
        RoleDTO role = dto.getRole();
        if (role != null) {
            entity.setRoleId(dto.getRole().getId());
        }
        entity.setEmail(dto.getEmail());
        if (password.isEmpty()) {
            UserDAOImpl userDAO = new UserDAOImpl();
            password = userDAO.readById(entity.getId()).getPassword();
        }
        entity.setPassword(password);
        entity.setStatus(entity.getStatus());
        return entity;
    }

}
