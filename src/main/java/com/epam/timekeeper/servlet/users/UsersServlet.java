package com.epam.timekeeper.servlet.users;

import com.epam.timekeeper.dto.UserDTO;
import com.epam.timekeeper.exception.DBException;
import com.epam.timekeeper.exception.DTOConversionException;
import com.epam.timekeeper.service.RoleService;
import com.epam.timekeeper.service.UserHasActivityService;
import com.epam.timekeeper.service.UserService;
import com.epam.timekeeper.servlet.util.SessionToRequestMessageMapper;

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
import static com.epam.timekeeper.servlet.util.constants.JspUrn.*;

@WebServlet(name = "UsersServlet", value = USERS)
public class UsersServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsersServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String lang = Arrays.stream(request.getCookies()).filter(c -> c.getName().equals("lang")).toList().get(0).getValue();
        SessionToRequestMessageMapper.map(request);
        HttpSession session = request.getSession();
        String logHeader = "session:" + session.getId() + ", username:"
                + ((UserDTO) session.getAttribute("user")).getUsername() + ". doGet -> ";
        try {
            UserService userService = new UserService();
            RoleService roleService = new RoleService();
            UserHasActivityService userHasActivityService = new UserHasActivityService();
            request.setAttribute("users", userService.getAll());
            request.setAttribute("roles", roleService.getAll());
            request.setAttribute("uha", userHasActivityService.getAllWithSummary());
            request.getRequestDispatcher(USERS_JSP).forward(request, response);
            LOGGER.info(logHeader + "Successfully complete.");
        } catch (DBException e) {
            LOGGER.error(logHeader + "DBException: " + e.getMessage());
            if(lang.equals("en")) {
                session.setAttribute("warningMessage", DB_EXCEPTION_MESSAGE);
            } else {
                session.setAttribute("warningMessage", DB_EXCEPTION_MESSAGE_UA);
            }
            response.sendRedirect("home");
        } catch (DTOConversionException e) {
            LOGGER.error(logHeader + "DTOConversionException: " + e.getMessage());
            if(lang.equals("en")) {
                session.setAttribute("errorMessage", DTO_CONVERSION_MESSAGE);
            } else {
                session.setAttribute("errorMessage", DTO_CONVERSION_MESSAGE_UA);
            }
            response.sendRedirect(getServletContext().getContextPath() + HOME);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(getServletContext().getContextPath() + USERS);
    }
}
