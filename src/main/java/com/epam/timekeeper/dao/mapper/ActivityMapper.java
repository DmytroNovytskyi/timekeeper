package com.epam.timekeeper.dao.mapper;

import com.epam.timekeeper.entity.Activity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ActivityMapper implements EntityMapper<Activity> {

    @Override
    public Activity map(ResultSet resultSet) throws SQLException {
        int i = 0;
        Activity activity = new Activity();
        activity.setId(resultSet.getInt(++i));
        activity.setCategoryID(resultSet.getInt(++i));
        activity.setName(resultSet.getString(++i));
        activity.setStatus(Activity.Status.valueOf(resultSet.getString(++i)));
        return activity;
    }

}
