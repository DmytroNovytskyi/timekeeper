package com.epam.timekeeper.dao;

public interface SQL {

    interface Activity {

        String CREATE = """
                INSERT INTO activity (category_id, name)
                VALUES (?, ?);""";
        String READ_ALL = """
                SELECT *
                FROM activity;""";
        String READ_BY_ID = """
                SELECT *
                FROM activity
                WHERE id = ?;""";
        String UPDATE_BY_ID = """
                UPDATE activity
                SET category_id = ?,
                    name        = ?,
                    status      = ?
                    WHERE id = ?;""";
        String DELETE_BY_ID = """
                DELETE
                FROM activity
                WHERE id = ?;""";

    }

    interface Category {

        String CREATE = """
                INSERT INTO category (name)
                VALUES (?);""";

        String READ_ALL = """
                SELECT *
                FROM category;""";

        String READ_BY_ID = """
                SELECT *
                FROM category
                WHERE id = ?;""";

        String UPDATE_BY_ID = """
                UPDATE category
                SET name   = ?,
                    status = ?
                WHERE id = ?;""";

        String DELETE_BY_ID = """
                DELETE
                FROM category
                WHERE id = ?;""";

    }

    interface Role {

        String CREATE = """
                INSERT INTO role (name)
                VALUES (?);""";

        String READ_ALL = """
                SELECT *
                FROM role;""";

        String READ_BY_ID = """
                SELECT *
                FROM role
                WHERE id = ?;""";

        String UPDATE_BY_ID = """
                UPDATE role
                SET name = ?
                WHERE id = ?;""";

        String DELETE_BY_ID = """
                DELETE
                FROM role
                WHERE id = ?;""";
    }

    interface User {

        String CREATE = """
                INSERT INTO user(username, role_id, email, password)
                VALUES (?, ?, ?, ?);""";

        String READ_ALL = """
                SELECT *
                FROM user;""";

        String READ_BY_ID = """
                SELECT *
                FROM user
                WHERE id = ?;""";

        String UPDATE_BY_ID = """
                UPDATE user
                SET username = ?,
                    email    = ?,
                    password = ?,
                    status   = ?
                WHERE id = ?;""";

        String DELETE_BY_ID = """
                DELETE
                FROM user
                WHERE id = ?;""";

    }

    interface UserHasActivity {

        String CREATE = """
                INSERT INTO user_has_activity(user_id, activity_id)
                VALUES (?, ?);""";

        String READ_ALL = """
                SELECT *
                FROM user_has_activity;""";

        String READ_BY_ID = """
                SELECT *
                FROM user_has_activity
                WHERE id = ?;""";

        String UPDATE_BY_ID = """
                UPDATE user_has_activity
                SET status      = ?,
                    start_time  = ?,
                    time_spent  = ?
                WHERE id = ?;""";

        String DELETE_BY_ID = """
                DELETE
                FROM user_has_activity
                WHERE id = ?;""";

    }

}
