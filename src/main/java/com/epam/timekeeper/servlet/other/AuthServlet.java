package com.epam.timekeeper.servlet.other;

import com.epam.timekeeper.dto.UserDTO;
import com.epam.timekeeper.entity.User;
import com.epam.timekeeper.exception.DBException;
import com.epam.timekeeper.exception.DTOConversionException;
import com.epam.timekeeper.service.UserService;
import com.epam.timekeeper.servlet.util.SessionToRequestMessageMapper;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "AuthServlet", urlPatterns = "/auth")
public class AuthServlet extends HttpServlet {

    private final static String WRONG_DATA_MESSAGE = "Wrong username or password!";
    private final static String INTERNAL_ERROR_MESSAGE = "Internal server error occurred. Please try again later.";
    private final static String WARNING_MESSAGE = "Database error occurred while trying to validate your data. Please try again later.";
    private final static String BANNED_USER_MESSAGE = "This user is banned.";
    private final static String REQUIREMENTS_MESSAGE = "Username, email or password doesn't match requirements. Please try again.";

    private final static String USERNAME_REGEX = "^(?=[a-zA-Z0-9._]{8,45}$)(?!.*[_.]{2})[^_.].*[^_.]$";
    private final static String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,32}$";

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionToRequestMessageMapper.map(request);
        request.getRequestDispatcher("view/other/auth.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService userService = new UserService();
        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String logHeader = "session:" + session.getId() + ", username:" + username + ". doPost -> ";
        if (username.matches(USERNAME_REGEX)
                && password.matches(PASSWORD_REGEX)) {
            try {
                UserDTO user = new UserDTO();
                user.setUsername(username);
                user = userService.validate(user, password);
                if (user != null) {
                    if (user.getStatus().equals(User.Status.BANNED)) {
                        session.setAttribute("errorMessage", BANNED_USER_MESSAGE);
                        response.sendRedirect("auth");
                        LOGGER.info(logHeader + "Banned user.");
                    } else {
                        session.setAttribute("user", user);
                        response.sendRedirect("home");
                        LOGGER.info(logHeader + "Successfully signed in.");
                    }
                } else {
                    session.setAttribute("errorMessage", WRONG_DATA_MESSAGE);
                    response.sendRedirect("auth");
                    LOGGER.info(logHeader + "Wrong username or password.");
                }
            } catch (DBException e) {
                LOGGER.error(logHeader + "DBException: " + e.getMessage());
                session.setAttribute("warningMessage", WARNING_MESSAGE);
                response.sendRedirect("auth");
            } catch (DTOConversionException e) {
                LOGGER.error(logHeader + "DTOConversionException: " + e.getMessage());
                session.setAttribute("warningMessage", INTERNAL_ERROR_MESSAGE);
                response.sendRedirect("auth");
            }
        } else {
            LOGGER.error(logHeader + "Passed data doesn't meet the requirements of username: " + USERNAME_REGEX + " or password:" + PASSWORD_REGEX);
            session.setAttribute("errorMessage", REQUIREMENTS_MESSAGE);
            response.sendRedirect("auth");
        }
    }

}