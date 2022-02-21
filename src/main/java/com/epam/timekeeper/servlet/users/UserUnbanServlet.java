package com.epam.timekeeper.servlet.users;

import com.epam.timekeeper.dto.UserDTO;
import com.epam.timekeeper.exception.DBException;
import com.epam.timekeeper.exception.ObjectNotFoundException;
import com.epam.timekeeper.service.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.timekeeper.servlet.util.constants.Messages.Users.*;
import static com.epam.timekeeper.servlet.util.constants.ServletUrn.*;

@WebServlet(name = "UserUnbanServlet", value = USER_UNBAN)
public class UserUnbanServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserUnbanServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String logHeader = "session:" + session.getId() + ", username:"
                + ((UserDTO) session.getAttribute("user")).getUsername() + ". doPost -> ";
        try {
            UserService userService = new UserService();
            String id = request.getParameter("id");
            UserDTO user = new UserDTO();
            user.setId(Integer.parseInt(id));
            userService.unban(user);
            session.setAttribute("successMessage", SUCCESS_UNBAN_MESSAGE);
            LOGGER.info(logHeader + "Successfully complete.");
        } catch (DBException e) {
            LOGGER.error(logHeader + "DBException: " + e.getMessage());
            session.setAttribute("warningMessage", DB_EXCEPTION_MESSAGE);
        } catch (ObjectNotFoundException e) {
            LOGGER.error(logHeader + "ObjectNotFoundException: " + e.getMessage());
            session.setAttribute("warningMessage", NOT_FOUND_MESSAGE);
        }
        response.sendRedirect(getServletContext().getContextPath() + USERS);
    }

}
