package com.epam.timekeeper.dao;

/**
 * Interface contains SQL constants that are used for creating prepared statements
 */
public interface SQL {

    interface Activity {

        String CREATE = """
                INSERT INTO activity (category_id, name, description)
                VALUES (?, ?, ?);""";
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
                    status      = ?,
                    description = ?
                    WHERE id = ?;""";

    }

    interface Category {

        String CREATE_CATEGORY_ID = """
                INSERT INTO category
                VALUES ();""";

        String GET_LANG_ID = """
                SELECT *
                FROM lang
                WHERE name = ?;""";

        String CREATE = """
                INSERT INTO category_has_lang (category_id, lang_id, name)
                VALUES (?, ?, ?);""";

        String READ_ALL_LANG_NAMES = """
                SELECT name
                from lang;""";

        String READ_ALL_CATEGORIES = """
                SELECT *
                FROM category;""";

        String READ_CATEGORY_NAME_WITH_LOCALE = """
                SELECT chl.name
                FROM category_has_lang chl
                         JOIN lang l on chl.lang_id = l.id
                WHERE chl.category_id = ?
                  AND l.name = ?;""";

        String READ_STATUS_BY_ID = """
                SELECT status
                FROM category
                WHERE id = ?;""";

        String UPDATE_STATUS = """
                UPDATE category
                SET status = ?
                WHERE id = ?;""";

        String READ_ID_FOR_LANG_NAME = """
                SELECT id
                FROM lang
                WHERE name = ?;""";

        String UPDATE_NAME = """
                UPDATE category_has_lang
                SET name = ?
                WHERE category_id = ?
                  AND lang_id = ?;""";

    }

    interface Role {

        String READ_ALL = """
                SELECT *
                FROM role;""";

        String READ_BY_ID = """
                SELECT *
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
                SET status     = ?,
                    start_time = ?,
                    end_time   = ?
                WHERE id = ?;""";

    }

}
