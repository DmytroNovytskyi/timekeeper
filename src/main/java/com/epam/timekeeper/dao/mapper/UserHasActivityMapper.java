package com.epam.timekeeper.dao.mapper;

import com.epam.timekeeper.entity.UserHasActivity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserHasActivityMapper implements EntityMapper<UserHasActivity> {

    @Override
    public UserHasActivity map(ResultSet resultSet) throws SQLException {
        int i = 0;
        UserHasActivity userHasActivity = new UserHasActivity();
        userHasActivity.setId(resultSet.getInt(++i));
        userHasActivity.setUserId(resultSet.getInt(++i));
        userHasActivity.setActivityId(resultSet.getInt(++i));
        userHasActivity.setStatus(UserHasActivity.Status.valueOf(resultSet.getString(++i)));
        userHasActivity.setStartTime(resultSet.getTimestamp(++i));
        userHasActivity.setEndTime(resultSet.getTimestamp(++i));
        userHasActivity.setCreationDate(resultSet.getTimestamp(++i));
        return userHasActivity;
    }
}
