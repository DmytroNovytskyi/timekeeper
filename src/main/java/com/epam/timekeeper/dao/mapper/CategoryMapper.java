package com.epam.timekeeper.dao.mapper;

import com.epam.timekeeper.entity.Category;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryMapper implements EntityMapper<Category> {

    @Override
    public Category map(ResultSet resultSet) throws SQLException {
        int i = 0;
        Category category = new Category();
        category.setId(resultSet.getInt(++i));
        category.setName(resultSet.getString(++i));
        category.setStatus(Category.Status.valueOf(resultSet.getString(++i)));
        return category;
    }

}
