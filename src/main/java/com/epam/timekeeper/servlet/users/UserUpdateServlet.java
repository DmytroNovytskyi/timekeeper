package com.epam.timekeeper.servlet.users;

import com.epam.timekeeper.dto.UserDTO;
import com.epam.timekeeper.exception.AlreadyExistsException;
import com.epam.timekeeper.exception.DBException;
import com.epam.timekeeper.exception.ObjectNotFoundException;
import com.epam.timekeeper.service.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "UserUpdateServlet", value = "/users/update")
public class UserUpdateServlet extends HttpServlet {

    private final static String SUCCESS_MESSAGE = "User successfully updated!";
    private final static String ALREADY_EXISTS_MESSAGE = "User with this username or email already exists!";
    private final static String WARNING_MESSAGE = "Database error occurred while trying to access users. Please try again later.";
    private final static String WRONG_DATA_MESSAGE = "Wrong username, email or password data. Please try again.";
    private final static String NOT_FOUND_MESSAGE = "User was not found.";

    private final static String USERNAME_REGEX = "^(?=[a-zA-Z0-9._]{8,45}$)(?!.*[_.]{2})[^_.].*[^_.]$";
    private final static String EMAIL_REGEX = "^(?=[a-zA-Z0-9._@%-]{6,255}$)[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}$";
    private final static String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,32}$";

    private static final Logger LOGGER = LoggerFactory.getLogger(UserUpdateServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();
        String logHeader = "session:" + session.getId() + ", username:" + ((UserDTO) session.getAttribute("user")).getUsername() + ". doPost -> ";
        if (username.matches(USERNAME_REGEX)
                && (email.isEmpty() || email.matches(EMAIL_REGEX))
                && (password.isEmpty() || password.matches(PASSWORD_REGEX))) {
            try {
                UserService userService = new UserService();
                userService.update(createDTO(userId, username, email), password);
                session.setAttribute("message", SUCCESS_MESSAGE);
                LOGGER.info(logHeader + "Successfully complete.");
            } catch (AlreadyExistsException e) {
                LOGGER.error(logHeader + "AlreadyExistsException: " + e.getMessage());
                session.setAttribute("message", ALREADY_EXISTS_MESSAGE);
            } catch (DBException e) {
                LOGGER.error(logHeader + "DBException: " + e.getMessage());
                session.setAttribute("warningMessage", WARNING_MESSAGE);
            } catch (ObjectNotFoundException e) {
                LOGGER.error(logHeader + "ObjectNotFoundException: " + e.getMessage());
                session.setAttribute("warningMessage", NOT_FOUND_MESSAGE);
            }
        } else {
            LOGGER.error(logHeader + "Passed data doesn't meet the requirements of username: " + USERNAME_REGEX
                    + " or email: " + EMAIL_REGEX + " or password: " + PASSWORD_REGEX);
            session.setAttribute("errorMessage", WRONG_DATA_MESSAGE);
        }
        response.sendRedirect(getServletContext().getContextPath() + "/users");
    }

    private UserDTO createDTO(int userId, String username, String email) {
        UserDTO user = new UserDTO();
        user.setId(userId);
        user.setUsername(username);
        user.setEmail(email);
        return user;
    }
}
