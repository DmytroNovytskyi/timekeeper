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

import static com.epam.timekeeper.servlet.util.constants.Messages.Users.*;
import static com.epam.timekeeper.servlet.util.constants.ServletUrn.*;

@WebServlet(name = "UserCreateServlet", value = USER_CREATE)
public class UserCreateServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserCreateServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int roleId = Integer.parseInt(request.getParameter("roleId"));
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String logHeader = "session:" + session.getId() + ", username:"
                + ((UserDTO) session.getAttribute("user")).getUsername() + ". doPost -> ";
        if (username.matches(USERNAME_REGEX)
                && (email.isEmpty() || email.matches(EMAIL_REGEX))
                && password.matches(PASSWORD_REGEX)) {
            try {
                UserService userService = new UserService();
                userService.create(createDTO(roleId, username, email), password);
                session.setAttribute("successMessage", SUCCESS_CREATE_MESSAGE);
                LOGGER.info(logHeader + "Successfully complete.");
            } catch (AlreadyExistsException e) {
                LOGGER.error(logHeader + "AlreadyExistsException: " + e.getMessage());
                session.setAttribute("errorMessage", ALREADY_EXISTS_MESSAGE);
            } catch (DBException e) {
                LOGGER.error(logHeader + "DBException: " + e.getMessage());
                session.setAttribute("warningMessage", DB_EXCEPTION_MESSAGE);
            }
        } else {
            LOGGER.error(logHeader + "Passed data doesn't meet the requirements of username: " + USERNAME_REGEX
                    + " or email: " + EMAIL_REGEX + " or password: " + PASSWORD_REGEX);
            session.setAttribute("errorMessage", REQUIREMENTS_MESSAGE);
        }
        response.sendRedirect(getServletContext().getContextPath() + USERS);
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
