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
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.timekeeper.servlet.util.constants.Messages.Activities.DB_EXCEPTION_MESSAGE;
import static com.epam.timekeeper.servlet.util.constants.Messages.Activities.DB_EXCEPTION_MESSAGE_UA;
import static com.epam.timekeeper.servlet.util.constants.Messages.Users.*;
import static com.epam.timekeeper.servlet.util.constants.ServletUrn.*;

@WebServlet(name = "UserCreateServlet", value = USER_CREATE)
public class UserCreateServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserCreateServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String lang = Arrays.stream(request.getCookies()).filter(c -> c.getName().equals("lang")).toList().get(0).getValue();
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
                LOGGER.info(logHeader + "Successfully complete.");
                if(lang.equals("en")) {
                    session.setAttribute("successMessage", SUCCESS_CREATE_MESSAGE);
                } else {
                    session.setAttribute("successMessage", SUCCESS_CREATE_MESSAGE_UA);
                }
            } catch (AlreadyExistsException e) {
                LOGGER.error(logHeader + "AlreadyExistsException: " + e.getMessage());
                if(lang.equals("en")) {
                    session.setAttribute("errorMessage", ALREADY_EXISTS_MESSAGE);
                } else {
                    session.setAttribute("errorMessage", ALREADY_EXISTS_MESSAGE_UA);
                }
            } catch (DBException e) {
                LOGGER.error(logHeader + "DBException: " + e.getMessage());
                if(lang.equals("en")) {
                    session.setAttribute("warningMessage", DB_EXCEPTION_MESSAGE);
                } else {
                    session.setAttribute("warningMessage", DB_EXCEPTION_MESSAGE_UA);
                }
            }
        } else {
            LOGGER.error(logHeader + "Passed data doesn't meet the requirements of username: " + USERNAME_REGEX
                    + " or email: " + EMAIL_REGEX + " or password: " + PASSWORD_REGEX);
            if(lang.equals("en")) {
                session.setAttribute("errorMessage", REQUIREMENTS_MESSAGE);
            } else {
                session.setAttribute("errorMessage", REQUIREMENTS_MESSAGE_UA);
            }
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
