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
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.timekeeper.servlet.util.constants.Messages.Activities.DB_EXCEPTION_MESSAGE;
import static com.epam.timekeeper.servlet.util.constants.Messages.Activities.DB_EXCEPTION_MESSAGE_UA;
import static com.epam.timekeeper.servlet.util.constants.Messages.Users.*;
import static com.epam.timekeeper.servlet.util.constants.ServletUrn.*;

@WebServlet(name = "UserUpdateServlet", value = USER_UPDATE)
public class UserUpdateServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserUpdateServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();
        String lang = Arrays.stream(request.getCookies()).filter(c -> c.getName().equals("lang")).toList().get(0).getValue();
        String logHeader = "session:" + session.getId() + ", username:"
                + ((UserDTO) session.getAttribute("user")).getUsername() + ". doPost -> ";
        if (username.matches(USERNAME_REGEX)
                && (email.isEmpty() || email.matches(EMAIL_REGEX))
                && (password.isEmpty() || password.matches(PASSWORD_REGEX))) {
            try {
                UserService userService = new UserService();
                userService.update(createDTO(userId, username, email), password);
                LOGGER.info(logHeader + "Successfully complete.");
                if(lang.equals("en")) {
                    session.setAttribute("message", SUCCESS_UPDATE_MESSAGE);
                } else {
                    session.setAttribute("message", SUCCESS_UPDATE_MESSAGE_UA);
                }
            } catch (AlreadyExistsException e) {
                LOGGER.error(logHeader + "AlreadyExistsException: " + e.getMessage());
                if(lang.equals("en")) {
                    session.setAttribute("message", ALREADY_EXISTS_MESSAGE);
                } else {
                    session.setAttribute("message", ALREADY_EXISTS_MESSAGE_UA);
                }
            } catch (DBException e) {
                LOGGER.error(logHeader + "DBException: " + e.getMessage());
                if(lang.equals("en")) {
                    session.setAttribute("warningMessage", DB_EXCEPTION_MESSAGE);
                } else {
                    session.setAttribute("warningMessage", DB_EXCEPTION_MESSAGE_UA);
                }
            } catch (ObjectNotFoundException e) {
                LOGGER.error(logHeader + "ObjectNotFoundException: " + e.getMessage());
                if(lang.equals("en")) {
                    session.setAttribute("warningMessage", NOT_FOUND_MESSAGE);
                } else {
                    session.setAttribute("warningMessage", NOT_FOUND_MESSAGE_UA);
                }
            }
        } else {
            LOGGER.error(logHeader + "Passed data doesn't meet the requirements of username: " + USERNAME_REGEX
                    + " or email: " + EMAIL_REGEX + " or password: " + PASSWORD_REGEX);
            if(lang.equals("en")) {
                session.setAttribute("errorMessage", REQUIREMENTS_MESSAGE);
            } else {
                session.setAttribute("errorMessage", REQUIREMENTS_MESSAGE);
            }
        }
        response.sendRedirect(getServletContext().getContextPath() + USERS);
    }

    private UserDTO createDTO(int userId, String username, String email) {
        UserDTO user = new UserDTO();
        user.setId(userId);
        user.setUsername(username);
        user.setEmail(email);
        return user;
    }

}
