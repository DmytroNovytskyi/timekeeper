package com.epam.timekeeper.servlet.activities;

import com.epam.timekeeper.dto.UserDTO;
import com.epam.timekeeper.dto.UserHasActivityDTO;
import com.epam.timekeeper.exception.AlreadyExistsException;
import com.epam.timekeeper.exception.DBException;
import com.epam.timekeeper.service.UserHasActivityService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.timekeeper.servlet.util.constants.Messages.Activities.*;
import static com.epam.timekeeper.servlet.util.constants.ServletUrn.*;

@WebServlet(name = "ActivityProcessServlet", value = ACTIVITIES_PROCESS)
public class ActivityProcessServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityProcessServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserHasActivityService userHasActivityService = new UserHasActivityService();
        HttpSession session = request.getSession();
        String lang = Arrays.stream(request.getCookies()).filter(c -> c.getName().equals("lang")).toList().get(0).getValue();
        int id = Integer.parseInt(request.getParameter("id"));
        String action = request.getParameter("action");
        UserHasActivityDTO userHasActivity = new UserHasActivityDTO();
        userHasActivity.setId(id);
        String logHeader = "session:" + session.getId() + ", username:" + ((UserDTO) session.getAttribute("user")).getUsername() + ". doPost -> ";
        try {
            switch (action) {
                case "start" -> {
                    userHasActivityService.start(userHasActivity);
                    LOGGER.info(logHeader + "Start successfully complete.");
                    if(lang.equals("en")) {
                        session.setAttribute("successMessage", SUCCESS_START_MESSAGE);
                    } else {
                        session.setAttribute("successMessage", SUCCESS_START_MESSAGE_UA);
                    }
                }
                case "end" -> {
                    userHasActivityService.end(userHasActivity);
                    LOGGER.info(logHeader + "End successfully complete.");
                    if(lang.equals("en")) {
                        session.setAttribute("successMessage", SUCCESS_END_MESSAGE);
                    } else {
                        session.setAttribute("successMessage", SUCCESS_END_MESSAGE_UA);
                    }
                }
                case "abort" -> {
                    userHasActivityService.requestAbort(userHasActivity);
                    LOGGER.info(logHeader + "Abort successfully complete.");
                    if(lang.equals("en")) {
                        session.setAttribute("successMessage", SUCCESS_ABORT_MESSAGE);
                    } else {
                        session.setAttribute("successMessage", SUCCESS_ABORT_MESSAGE_UA);
                    }
                }
            }
        } catch (AlreadyExistsException e) {
            LOGGER.error(logHeader + "AlreadyExistsException: " + e.getMessage());
            if(lang.equals("en")) {
                session.setAttribute("errorMessage", REQUEST_ALREADY_EXISTS_MESSAGE);
            } else {
                session.setAttribute("errorMessage", REQUEST_ALREADY_EXISTS_MESSAGE_UA);
            }
        } catch (DBException e) {
            LOGGER.error(logHeader + "DBException: " + e.getMessage());
            if(lang.equals("en")) {
                session.setAttribute("warningMessage", DB_EXCEPTION_MESSAGE);
            } else {
                session.setAttribute("warningMessage", DB_EXCEPTION_MESSAGE_UA);
            }
        }
        response.sendRedirect(getServletContext().getContextPath() + ACTIVITIES);
    }

}
