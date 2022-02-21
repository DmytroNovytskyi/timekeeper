package com.epam.timekeeper.servlet.util.constants;

public interface Messages {

    interface Activities {
        String DTO_CONVERSION_MESSAGE = "Internal server error occurred. Please try again later.";
        String DB_EXCEPTION_MESSAGE = "Database error occurred. Please try again later.";
        String REQUEST_ALREADY_EXISTS_MESSAGE = "Request already exists!";
        String ACTIVITY_ALREADY_EXISTS_MESSAGE = "Activity already exists!";
        String NOT_FOUND_MESSAGE = "Activity was not found.";
        String REQUIREMENTS_MESSAGE = "Activity doesn't match requirements. Please try again.";
        String ACTIVITY_OPEN_EXCEPTION_MESSAGE = "Activity cannot be opened because category is closed.";
        String SUCCESS_REQUEST_MESSAGE = "Request successfully created!";
        String SUCCESS_CLOSE_MESSAGE = "Activity successfully closed.";
        String SUCCESS_CREATE_MESSAGE = "Activity successfully created!";
        String SUCCESS_OPEN_MESSAGE = "Activity successfully opened.";
        String SUCCESS_START_MESSAGE = "Activity successfully started!";
        String SUCCESS_END_MESSAGE = "Activity successfully ended!";
        String SUCCESS_ABORT_MESSAGE = "Abort successfully requested!";
        String SUCCESS_UPDATE_MESSAGE = "Activity successfully updated!";
        String ACTIVITY_NAME_REGEX = "^[\\sa-zA-Z0-9/.-]{8,45}$";
    }

    interface Categories {
        String DTO_CONVERSION_MESSAGE = "Internal server error occurred. Please try again later.";
        String DB_EXCEPTION_MESSAGE = "Database error occurred. Please try again later.";
        String NOT_FOUND_MESSAGE = "Category was not found.";
        String ALREADY_EXISTS_MESSAGE = "Category with this name already exists!";
        String REQUIREMENTS_MESSAGE = "Category doesn't match requirements. Please try again.";
        String SUCCESS_CLOSE_MESSAGE = "Category successfully closed.";
        String SUCCESS_CREATE_MESSAGE = "Category successfully created.";
        String SUCCESS_OPEN_MESSAGE = "Category successfully opened.";
        String SUCCESS_UPDATE_MESSAGE = "Category successfully updated.";
        String CATEGORY_NAME_REGEX = "^[\\sa-zA-Z0-9/.-]{8,45}$";
    }

    interface Other {
        String DTO_CONVERSION_MESSAGE = "Internal server error occurred. Please try again later.";
        String DB_EXCEPTION_MESSAGE = "Database error occurred. Please try again later.";
        String WRONG_DATA_MESSAGE = "Wrong username or password!";
        String BANNED_USER_MESSAGE = "This user is banned.";
        String REQUIREMENTS_MESSAGE = "Username or password doesn't match requirements. Please try again.";
        String USERNAME_REGEX = "^(?=[a-zA-Z0-9._]{8,45}$)(?!.*[_.]{2})[^_.].*[^_.]$";
        String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,32}$";
    }

    interface Requests {
        String DTO_CONVERSION_MESSAGE = "Internal server error occurred. Please try again later.";
        String DB_EXCEPTION_MESSAGE = "Database error occurred. Please try again later.";
        String SUCCESS_PROCESS_MESSAGE = "Request successfully processed.";
    }

    interface Users {
        String DTO_CONVERSION_MESSAGE = "Internal server error occurred. Please try again later.";
        String DB_EXCEPTION_MESSAGE = "Database error occurred. Please try again later.";
        String NOT_FOUND_MESSAGE = "User was not found.";
        String ALREADY_EXISTS_MESSAGE = "User with this username or email already exists!";
        String REQUIREMENTS_MESSAGE = "Username, email or password doesn't match requirements. Please try again.";
        String SUCCESS_BAN_MESSAGE = "User successfully banned.";
        String SUCCESS_UNBAN_MESSAGE = "User successfully unbanned.";
        String SUCCESS_CREATE_MESSAGE = "User successfully created.";
        String SUCCESS_UPDATE_MESSAGE = "User successfully updated.";
        String USERNAME_REGEX = "^(?=[a-zA-Z0-9._]{8,45}$)(?!.*[_.]{2})[^_.].*[^_.]$";
        String EMAIL_REGEX = "^(?=[a-zA-Z0-9._@%-]{6,255}$)[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}$";
        String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,32}$";
    }

}
