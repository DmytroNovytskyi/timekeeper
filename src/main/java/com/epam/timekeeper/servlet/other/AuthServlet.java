package com.epam.timekeeper.servlet.other;

import com.epam.timekeeper.dto.UserDTO;
import com.epam.timekeeper.entity.User;
import com.epam.timekeeper.exception.DBException;
import com.epam.timekeeper.exception.DTOConversionException;
import com.epam.timekeeper.service.UserService;
import com.epam.timekeeper.servlet.util.SessionToRequestMessageMapper;

import java.io.*;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.timekeeper.servlet.util.constants.Messages.Activities.DB_EXCEPTION_MESSAGE;
import static com.epam.timekeeper.servlet.util.constants.Messages.Activities.DB_EXCEPTION_MESSAGE_UA;
import static com.epam.timekeeper.servlet.util.constants.Messages.Other.*;
import static com.epam.timekeeper.servlet.util.constants.ServletUrn.*;
import static com.epam.timekeeper.servlet.util.constants.JspUrn.*;

@WebServlet(name = "AuthServlet", value = AUTH)
public class AuthServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionToRequestMessageMapper.map(request);
        request.getRequestDispatcher(AUTH_JSP).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService userService = new UserService();
        HttpSession session = request.getSession();
        String lang = Arrays.stream(request.getCookies()).filter(c -> c.getName().equals("lang")).toList().get(0).getValue();
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
                        if(lang.equals("en")) {
                            session.setAttribute("errorMessage", BANNED_USER_MESSAGE);
                        } else {
                            session.setAttribute("errorMessage", BANNED_USER_MESSAGE_UA);
                        }
                        response.sendRedirect(getServletContext().getContextPath() + AUTH);
                        LOGGER.info(logHeader + "Banned user.");
                    } else {
                        session.setAttribute("user", user);
                        response.sendRedirect(getServletContext().getContextPath() + HOME);
                        LOGGER.info(logHeader + "Successfully signed in.");
                    }
                } else {
                    if(lang.equals("en")) {
                        session.setAttribute("errorMessage", WRONG_DATA_MESSAGE);
                    } else {
                        session.setAttribute("errorMessage", WRONG_DATA_MESSAGE_UA);
                    }
                    response.sendRedirect(getServletContext().getContextPath() + AUTH);
                    LOGGER.info(logHeader + "Wrong username or password.");
                }
            } catch (DBException e) {
                LOGGER.error(logHeader + "DBException: " + e.getMessage());
                if(lang.equals("en")) {
                    session.setAttribute("warningMessage", DB_EXCEPTION_MESSAGE);
                } else {
                    session.setAttribute("warningMessage", DB_EXCEPTION_MESSAGE_UA);
                }
                response.sendRedirect(getServletContext().getContextPath() + AUTH);
            } catch (DTOConversionException e) {
                LOGGER.error(logHeader + "DTOConversionException: " + e.getMessage());
                if(lang.equals("en")) {
                    session.setAttribute("warningMessage", DTO_CONVERSION_MESSAGE);
                } else {
                    session.setAttribute("warningMessage", DTO_CONVERSION_MESSAGE_UA);
                }
                response.sendRedirect(getServletContext().getContextPath() + AUTH);
            }
        } else {
            LOGGER.error(logHeader + "Passed data doesn't meet the requirements of username: " + USERNAME_REGEX
                    + " or password:" + PASSWORD_REGEX);
            if(lang.equals("en")) {
                session.setAttribute("errorMessage", REQUIREMENTS_MESSAGE);
            } else {
                session.setAttribute("errorMessage", REQUIREMENTS_MESSAGE_UA);
            }
            response.sendRedirect(getServletContext().getContextPath() + AUTH);
        }
    }

}