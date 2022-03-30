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
        String DTO_CONVERSION_MESSAGE_UA = "Сталася внутрішня помилка сервера. Будь-ласка спробуйте пізніше.";
        String DB_EXCEPTION_MESSAGE_UA = "Сталася помилка бази даних. Будь-ласка спробуйте пізніше.";
        String REQUEST_ALREADY_EXISTS_MESSAGE_UA = "Запит уже існує!";
        String ACTIVITY_ALREADY_EXISTS_MESSAGE_UA = "Активність уже існує!";
        String NOT_FOUND_MESSAGE_UA = "Активність не знайдено.";
        String REQUIREMENTS_MESSAGE_UA = "Активність не відповідає вимогам. Будь ласка спробуйте ще раз.";
        String ACTIVITY_OPEN_EXCEPTION_MESSAGE_UA = "Активність не може бути відкрита, оскільки категорія закрита.";
        String SUCCESS_REQUEST_MESSAGE_UA = "Запит успішно створено!";
        String SUCCESS_CLOSE_MESSAGE_UA = "Активність успішно закрита.";
        String SUCCESS_CREATE_MESSAGE_UA = "Активність створено!";
        String SUCCESS_OPEN_MESSAGE_UA = "Активність успішно відкрита.";
        String SUCCESS_START_MESSAGE_UA = "Активність успішно розпочата!";
        String SUCCESS_END_MESSAGE_UA = "Активність успішно завершена!";
        String SUCCESS_ABORT_MESSAGE_UA = "Запит на скасування успішно надіслано!";
        String SUCCESS_UPDATE_MESSAGE_UA = "Активність успішно оновлена!";
        String ACTIVITY_NAME_REGEX = "^[\\sa-zA-Z0-9/.-]{8,45}$";
        String ACTIVITY_DESCRIPTION_REGEX = "^.{0,256}$";
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
        String DTO_CONVERSION_MESSAGE_UA = "Сталася внутрішня помилка сервера. Будь-ласка спробуйте пізніше.";
        String DB_EXCEPTION_MESSAGE_UA = "Сталася помилка бази даних. Будь-ласка спробуйте пізніше.";
        String NOT_FOUND_MESSAGE_UA = "Категорію не знайдено.";
        String ALREADY_EXISTS_MESSAGE_UA = "Категорія з такою назвою вже існує!";
        String REQUIREMENTS_MESSAGE_UA = "Категорія не відповідає вимогам. Будь ласка спробуйте ще раз.";
        String SUCCESS_CLOSE_MESSAGE_UA = "Категорію успішно закрито.";
        String SUCCESS_CREATE_MESSAGE_UA = "Категорію успішно створено.";
        String SUCCESS_OPEN_MESSAGE_UA = "Категорію успішно відкрито.";
        String SUCCESS_UPDATE_MESSAGE_UA = "Категорію успішно оновлено.";
        String CATEGORY_NAME_REGEX = "^[\\sa-zA-Z0-9/.-]{8,45}$";
    }

    interface Monitoring{
        String DTO_CONVERSION_MESSAGE = "Internal server error occurred. Please try again later.";
        String DB_EXCEPTION_MESSAGE = "Database error occurred. Please try again later.";
        String DTO_CONVERSION_MESSAGE_UA = "Internal server error occurred. Please try again later.";
        String DB_EXCEPTION_MESSAGE_UA = "Database error occurred. Please try again later.";
    }

    interface Other {
        String DTO_CONVERSION_MESSAGE = "Internal server error occurred. Please try again later.";
        String DB_EXCEPTION_MESSAGE = "Database error occurred. Please try again later.";
        String WRONG_DATA_MESSAGE = "Wrong username or password!";
        String BANNED_USER_MESSAGE = "This user is banned.";
        String REQUIREMENTS_MESSAGE = "Username or password doesn't match requirements. Please try again.";
        String DTO_CONVERSION_MESSAGE_UA = "Сталася внутрішня помилка сервера. Будь-ласка спробуйте пізніше.";
        String DB_EXCEPTION_MESSAGE_UA = "Сталася помилка бази даних. Будь-ласка спробуйте пізніше.";
        String WRONG_DATA_MESSAGE_UA = "Неправильне ім'я користувача або пароль!";
        String BANNED_USER_MESSAGE_UA = "Цей користувач забанений.";
        String REQUIREMENTS_MESSAGE_UA = "Ім'я користувача або пароль не відповідають вимогам. Будь ласка спробуйте ще раз.";
        String USERNAME_REGEX = "^(?=[a-zA-Z0-9._]{8,45}$)(?!.*[_.]{2})[^_.].*[^_.]$";
        String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,32}$";
    }

    interface Requests {
        String DTO_CONVERSION_MESSAGE = "Internal server error occurred. Please try again later.";
        String DB_EXCEPTION_MESSAGE = "Database error occurred. Please try again later.";
        String SUCCESS_PROCESS_MESSAGE = "Request successfully processed.";
        String DTO_CONVERSION_MESSAGE_UA = "Сталася внутрішня помилка сервера. Будь-ласка спробуйте пізніше.";
        String DB_EXCEPTION_MESSAGE_UA = "Сталася помилка бази даних. Будь-ласка спробуйте пізніше.";
        String SUCCESS_PROCESS_MESSAGE_UA = "Запит успішно опрацьовано.";
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
        String DTO_CONVERSION_MESSAGE_UA = "Сталася внутрішня помилка сервера. Будь-ласка спробуйте пізніше.";
        String DB_EXCEPTION_MESSAGE_UA = "Сталася помилка бази даних. Будь-ласка спробуйте пізніше.";
        String NOT_FOUND_MESSAGE_UA = "Користувача не знайдено.";
        String ALREADY_EXISTS_MESSAGE_UA = "Користувач із цим іменем користувача або електронною адресою вже існує!";
        String REQUIREMENTS_MESSAGE_UA = "Ім'я користувача, електронна адреса або пароль не відповідають вимогам. Будь ласка спробуйте ще раз.";
        String SUCCESS_BAN_MESSAGE_UA = "Користувача успішно заблоковано.";
        String SUCCESS_UNBAN_MESSAGE_UA = "Користувача успішно розблоковано.";
        String SUCCESS_CREATE_MESSAGE_UA = "Користувача створено.";
        String SUCCESS_UPDATE_MESSAGE_UA = "Користувача успішно оновлено";
        String USERNAME_REGEX = "^(?=[a-zA-Z0-9._]{8,45}$)(?!.*[_.]{2})[^_.].*[^_.]$";
        String EMAIL_REGEX = "^(?=[a-zA-Z0-9._@%-]{6,255}$)[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}$";
        String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,32}$";
    }

}
