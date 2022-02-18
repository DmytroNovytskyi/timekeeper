package com.epam.timekeeper.service;

import com.epam.timekeeper.dao.DAO;
import com.epam.timekeeper.dao.mapper.RoleMapper;
import com.epam.timekeeper.dao.preparer.RolePreparer;
import com.epam.timekeeper.dto.RoleDTO;
import com.epam.timekeeper.entity.Role;
import com.epam.timekeeper.service.mapper.RoleDTOMapper;

import java.util.List;
import java.util.stream.Collectors;

public class RoleService {

    private final DAO<Role> roleDAO = new DAO<>(new RolePreparer(), new RoleMapper());

    public List<RoleDTO> getAll(){
        List<Role> roles = roleDAO.readAll();
        if(roles == null){
            return null;
        }
        return roles.stream().map(RoleDTOMapper::toDTO).collect(Collectors.toList());
    }

}
