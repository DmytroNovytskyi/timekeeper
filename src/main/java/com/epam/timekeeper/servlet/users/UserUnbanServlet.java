package com.epam.timekeeper.servlet.users;

import com.epam.timekeeper.dto.UserDTO;
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

import static com.epam.timekeeper.servlet.util.constants.Messages.Users.*;
import static com.epam.timekeeper.servlet.util.constants.ServletUrn.*;

/**
 * Processes admin request to change user status to unblocked.
 * Unblocked users can access web application as normal.
 */
@WebServlet(name = "UserUnbanServlet", value = USER_UNBAN)
public class UserUnbanServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserUnbanServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String lang = Arrays.stream(request.getCookies()).filter(c -> c.getName().equals("lang")).toList().get(0).getValue();
        String logHeader = "session:" + session.getId() + ", username:"
                + ((UserDTO) session.getAttribute("user")).getUsername() + ". doPost -> ";
        try {
            UserService userService = new UserService();
            String id = request.getParameter("id");
            UserDTO user = new UserDTO();
            user.setId(Integer.parseInt(id));
            userService.unban(user);
            LOGGER.info(logHeader + "Successfully complete.");
            if(lang.equals("en")) {
                session.setAttribute("successMessage", SUCCESS_UNBAN_MESSAGE);
            } else {
                session.setAttribute("successMessage", SUCCESS_UNBAN_MESSAGE_UA);
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
        response.sendRedirect(getServletContext().getContextPath() + USERS);
    }

}
