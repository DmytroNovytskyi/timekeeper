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
        int id = Integer.parseInt(request.getParameter("id"));
        String action = request.getParameter("action");
        UserHasActivityDTO userHasActivity = new UserHasActivityDTO();
        userHasActivity.setId(id);
        String logHeader = "session:" + session.getId() + ", username:" + ((UserDTO) session.getAttribute("user")).getUsername() + ". doPost -> ";
        try {
            switch (action) {
                case "start" -> {
                    userHasActivityService.start(userHasActivity);
                    session.setAttribute("successMessage", SUCCESS_START_MESSAGE);
                    LOGGER.info(logHeader + "Start successfully complete.");
                }
                case "end" -> {
                    userHasActivityService.end(userHasActivity);
                    session.setAttribute("successMessage", SUCCESS_END_MESSAGE);
                    LOGGER.info(logHeader + "End successfully complete.");
                }
                case "abort" -> {
                    userHasActivityService.requestAbort(userHasActivity);
                    session.setAttribute("successMessage", SUCCESS_ABORT_MESSAGE);
                    LOGGER.info(logHeader + "Abort successfully complete.");
                }
            }
        } catch (AlreadyExistsException e) {
            LOGGER.error(logHeader + "AlreadyExistsException: " + e.getMessage());
            session.setAttribute("errorMessage", REQUEST_ALREADY_EXISTS_MESSAGE);
        } catch (DBException e) {
            LOGGER.error(logHeader + "DBException: " + e.getMessage());
            session.setAttribute("warningMessage", DB_EXCEPTION_MESSAGE);
        }
        response.sendRedirect(getServletContext().getContextPath() + ACTIVITIES);
    }

}
