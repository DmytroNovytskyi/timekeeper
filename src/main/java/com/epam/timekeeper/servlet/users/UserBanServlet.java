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

import static com.epam.timekeeper.servlet.util.constants.Messages.Activities.DB_EXCEPTION_MESSAGE;
import static com.epam.timekeeper.servlet.util.constants.Messages.Activities.DB_EXCEPTION_MESSAGE_UA;
import static com.epam.timekeeper.servlet.util.constants.Messages.Users.*;
import static com.epam.timekeeper.servlet.util.constants.ServletUrn.*;

@WebServlet(name = "UserBanServlet", value = USER_BAN)
public class UserBanServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserBanServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String logHeader = "session:" + session.getId() + ", username:"
                + ((UserDTO) session.getAttribute("user")).getUsername() + ". doPost -> ";
        String lang = Arrays.stream(request.getCookies()).filter(c -> c.getName().equals("lang")).toList().get(0).getValue();
        try {
            UserService userService = new UserService();
            String id = request.getParameter("id");
            UserDTO user = new UserDTO();
            user.setId(Integer.parseInt(id));
            userService.ban(user);
            LOGGER.info(logHeader + "Successfully complete.");
            if(lang.equals("en")) {
                session.setAttribute("successMessage", SUCCESS_BAN_MESSAGE);
            } else {
                session.setAttribute("successMessage", SUCCESS_BAN_MESSAGE_UA);
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
