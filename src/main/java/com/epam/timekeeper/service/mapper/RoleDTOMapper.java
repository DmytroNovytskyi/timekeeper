package com.epam.timekeeper.service.mapper;

import com.epam.timekeeper.dto.RoleDTO;
import com.epam.timekeeper.entity.Role;

public class RoleDTOMapper {

    private RoleDTOMapper() {
    }

    public static RoleDTO toDTO(Role entity) {
        RoleDTO dto = new RoleDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }

    public static Role toEntity(RoleDTO dto) {
        Role entity = new Role();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        return entity;
    }

}
