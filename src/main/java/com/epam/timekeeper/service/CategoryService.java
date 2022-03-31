package com.epam.timekeeper.service;

import com.epam.timekeeper.dao.impl.CategoryDAOImpl;
import com.epam.timekeeper.dto.CategoryDTO;
import com.epam.timekeeper.dto.FullCategoryDTO;
import com.epam.timekeeper.entity.Category;
import com.epam.timekeeper.exception.ObjectNotFoundException;
import com.epam.timekeeper.service.mapper.CategoryDTOMapper;
import com.epam.timekeeper.service.mapper.FullCategoryDTOMapper;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryService {

    private final CategoryDAOImpl categoryDAO = new CategoryDAOImpl();

    public List<FullCategoryDTO> getFullAll() {
        List<Category> categories = categoryDAO.readAll();
        if (categories == null) {
            return null;
        }
        return categories.stream()
                .map(FullCategoryDTOMapper::toDTO)
                .sorted(Comparator.comparing(c -> c.getLangName().get("en")))
                .collect(Collectors.toList());
    }

    public List<CategoryDTO> getAllOpened(String lang) {
        List<Category> categories = categoryDAO.readAll();
        if (categories == null) {
            return null;
        }
        return categories.stream()
                .filter(c -> c.getStatus().equals(Category.Status.OPENED))
                .map(c -> CategoryDTOMapper.toDTO(c, lang))
                .sorted(Comparator.comparing(CategoryDTO::getName))
                .collect(Collectors.toList());
    }

    public void update(FullCategoryDTO category) {
        Category entity = categoryDAO.readById(category.getId());
        if (entity == null) {
            throw new ObjectNotFoundException("Couldn't find category with id = " + category.getId() + " in database.");
        }
        entity.setLangName(category.getLangName());
        categoryDAO.update(entity);
    }

    public void create(FullCategoryDTO category) {
        categoryDAO.create(FullCategoryDTOMapper.toEntity(category));
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
