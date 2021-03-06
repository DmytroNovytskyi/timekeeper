package com.epam.timekeeper.service.mapper;

import com.epam.timekeeper.dto.RoleDTO;
import com.epam.timekeeper.entity.Role;

/**
 * Maps Role data transfer object to pass to servlet layer.
 */
public class RoleDTOMapper {

    private RoleDTOMapper() {
    }

    public static RoleDTO toDTO(Role entity) {
        RoleDTO dto = new RoleDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }

}
