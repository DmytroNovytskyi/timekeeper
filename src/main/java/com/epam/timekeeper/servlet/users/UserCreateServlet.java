package com.epam.timekeeper.servlet.users;

import com.epam.timekeeper.dto.RoleDTO;
import com.epam.timekeeper.dto.UserDTO;
import com.epam.timekeeper.exception.AlreadyExistsException;
import com.epam.timekeeper.exception.DBException;
import com.epam.timekeeper.service.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "UserCreateServlet", value = "/users/create")
public class UserCreateServlet extends HttpServlet {

    private final static String SUCCESS_MESSAGE = "User successfully created!";
    private final static String ALREADY_EXISTS_MESSAGE = "User with this username or email already exists!";
    private final static String WARNING_MESSAGE = "Database error occurred while trying to create user. Please try again later.";
    private final static String REQUIREMENTS_MESSAGE = "Username, email or password doesn't match requirements. Please try again.";

    private final static String USERNAME_REGEX = "^(?=[a-zA-Z0-9._]{8,45}$)(?!.*[_.]{2})[^_.].*[^_.]$";
    private final static String EMAIL_REGEX = "^(?=[a-zA-Z0-9._@%-]{6,255}$)[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}$";
    private final static String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,32}$";

    private static final Logger LOGGER = LoggerFactory.getLogger(UserCreateServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int roleId = Integer.parseInt(request.getParameter("roleId"));
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String logHeader = "session:" + session.getId() + ", username:" + ((UserDTO) session.getAttribute("user")).getUsername() + ". doPost -> ";
        if (username.matches(USERNAME_REGEX)
                && (email.isEmpty() || email.matches(EMAIL_REGEX))
                && password.matches(PASSWORD_REGEX)) {
            try {
                UserService userService = new UserService();
                userService.create(createDTO(roleId, username, email), password);
                session.setAttribute("successMessage", SUCCESS_MESSAGE);
                LOGGER.info(logHeader + "Successfully complete.");
            } catch (AlreadyExistsException e) {
                LOGGER.error(logHeader + "AlreadyExistsException: " + e.getMessage());
                session.setAttribute("errorMessage", ALREADY_EXISTS_MESSAGE);
            } catch (DBException e) {
                LOGGER.error(logHeader + "DBException: " + e.getMessage());
                session.setAttribute("warningMessage", WARNING_MESSAGE);
            }
        } else {
            LOGGER.error(logHeader + "Passed data doesn't meet the requirements of username: " + USERNAME_REGEX
                    + " or email: " + EMAIL_REGEX + " or password: " + PASSWORD_REGEX);
            session.setAttribute("errorMessage", REQUIREMENTS_MESSAGE);
        }
        response.sendRedirect(getServletContext().getContextPath() + "/users");
    }

    private UserDTO createDTO(int roleId, String username, String email) {
        UserDTO user = new UserDTO();
        RoleDTO role = new RoleDTO();
        role.setId(roleId);
        user.setRole(role);
        user.setUsername(username);
        user.setEmail(email);
        return user;
    }
}
