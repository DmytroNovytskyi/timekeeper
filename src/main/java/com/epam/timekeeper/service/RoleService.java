package com.epam.timekeeper.service;

import com.epam.timekeeper.dao.impl.RoleDAOImpl;
import com.epam.timekeeper.dto.RoleDTO;
import com.epam.timekeeper.entity.Role;
import com.epam.timekeeper.service.mapper.RoleDTOMapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents the part of service layer that works with roles.
 */
public class RoleService {

    private final RoleDAOImpl roleDAO = new RoleDAOImpl();

    public List<RoleDTO> getAll(){
        List<Role> roles = roleDAO.readAll();
        if(roles == null){
            return null;
        }
        return roles.stream().map(RoleDTOMapper::toDTO).collect(Collectors.toList());
    }

}
