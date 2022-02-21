package com.epam.timekeeper.service;

import com.epam.timekeeper.dao.DAO;
import com.epam.timekeeper.dao.mapper.CategoryMapper;
import com.epam.timekeeper.dao.preparer.CategoryPreparer;
import com.epam.timekeeper.dto.CategoryDTO;
import com.epam.timekeeper.entity.Activity;
import com.epam.timekeeper.entity.Category;
import com.epam.timekeeper.exception.ObjectNotFoundException;
import com.epam.timekeeper.service.mapper.CategoryDTOMapper;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryService {

    private final DAO<Category> categoryDAO = new DAO<>(new CategoryPreparer(), new CategoryMapper());

    public List<CategoryDTO> getAll() {
        List<Category> categories = categoryDAO.readAll();
        if (categories == null) {
            return null;
        }
        return categories.stream()
                .map(CategoryDTOMapper::toDTO)
                .sorted(Comparator.comparing(CategoryDTO::getName))
                .collect(Collectors.toList());
    }

    public CategoryDTO get(CategoryDTO dto) {
        Category entity = categoryDAO.readById(dto.getId());
        return entity == null ? null : CategoryDTOMapper.toDTO(entity);
    }

    public List<CategoryDTO> getAllOpened() {
        List<Category> categories = categoryDAO.readAll();
        if (categories == null) {
            return null;
        }
        return categories.stream()
                .filter(c -> c.getStatus().equals(Category.Status.OPENED))
                .map(CategoryDTOMapper::toDTO)
                .sorted(Comparator.comparing(CategoryDTO::getName))
                .collect(Collectors.toList());
    }

    public void update(CategoryDTO category) {
        Category entity = categoryDAO.readById(category.getId());
        if (entity == null) {
            throw new ObjectNotFoundException("Couldn't find category with id = " + category.getId() + " in database.");
        }
        entity.setName(category.getName());
        categoryDAO.update(entity);
    }

    public void create(CategoryDTO category) {
        categoryDAO.create(CategoryDTOMapper.toEntity(category));
    }

    public void open(CategoryDTO category) {
        Category entity = categoryDAO.readById(category.getId());
        if (entity == null) {
            throw new ObjectNotFoundException("Couldn't find category with id = " + category.getId() + " in database.");
        }
        entity.setStatus(Category.Status.OPENED);
        categoryDAO.update(entity);
    }

    public void close(CategoryDTO category) {
        Category entity = categoryDAO.readById(category.getId());
        if (entity == null) {
            throw new ObjectNotFoundException("Couldn't find category with id = " + category.getId() + " in database.");
        }
        entity.setStatus(Category.Status.CLOSED);
        categoryDAO.update(entity);
    }

}
