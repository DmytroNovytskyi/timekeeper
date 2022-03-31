package com.epam.timekeeper.service;

import com.epam.timekeeper.dao.impl.UserDAOImpl;
import com.epam.timekeeper.dto.UserDTO;
import com.epam.timekeeper.entity.User;
import com.epam.timekeeper.exception.ObjectNotFoundException;
import com.epam.timekeeper.service.mapper.UserDTOMapper;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class UserService {

    UserDAOImpl userDAO = new UserDAOImpl();

    public UserDTO validate(UserDTO user, String password) {
        List<User> users = userDAO.readAll();
        if (users == null) {
            return null;
        }
        String username = user.getUsername();
        users = users.stream()
                .filter(u -> u.getUsername().equals(username)
                && u.getPassword().equals(password))
                .collect(Collectors.toList());
        if (users.size() > 0) {
            return UserDTOMapper.toDTO(users.get(0));
        }
        return null;
    }

    public List<UserDTO> getAll() {
        List<User> users = userDAO.readAll();
        if (users == null) {
            return null;
        }
        return users.stream()
                .map(UserDTOMapper::toDTO)
                .sorted(Comparator.comparing(UserDTO::getUsername))
                .collect(Collectors.toList());
    }

    public UserDTO get(UserDTO user) {
        User entity = userDAO.readById(user.getId());
        return entity == null ? null : UserDTOMapper.toDTO(entity);
    }

    public void update(UserDTO user, String password) {
        User entity = userDAO.readById(user.getId());
        if (entity == null) {
            throw new ObjectNotFoundException("Couldn't find user with id = " + user.getId() + " in database.");
        }
        entity.setUsername(user.getUsername());
        entity.setEmail(user.getEmail());
        entity.setPassword(password.isEmpty() ? entity.getPassword() : password);
        userDAO.update(entity);
    }

    public void create(UserDTO user, String password) {
        userDAO.create(UserDTOMapper.toEntity(user, password));
    }

    public void ban(UserDTO user) {
        User entity = userDAO.readById(user.getId());
        if (entity == null) {
           throw new ObjectNotFoundException("Couldn't find user with id = " + user.getId() + " in database.");
        }
        entity.setStatus(User.Status.BANNED);
        userDAO.update(entity);
    }

    public void unban(UserDTO user) {
        User entity = userDAO.readById(user.getId());
        if (entity == null) {
            throw new ObjectNotFoundException("Couldn't find user with id = " + user.getId() + " in database.");
        }
        entity.setStatus(User.Status.ACTIVE);
        userDAO.update(entity);
    }

}
