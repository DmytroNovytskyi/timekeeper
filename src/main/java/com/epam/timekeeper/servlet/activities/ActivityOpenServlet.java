package com.epam.timekeeper.servlet.activities;

import com.epam.timekeeper.dto.ActivityDTO;
import com.epam.timekeeper.dto.UserDTO;
import com.epam.timekeeper.exception.ActivityOpenException;
import com.epam.timekeeper.exception.DBException;
import com.epam.timekeeper.exception.ObjectNotFoundException;
import com.epam.timekeeper.service.ActivityService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "ActivityOpenServlet", value = "/activities/open")
public class ActivityOpenServlet extends HttpServlet {

    private final static String SUCCESS_MESSAGE = "Activity successfully opened.";
    private final static String ERROR_MESSAGE = "Activity cannot be opened because category is closed.";
    private final static String WARNING_MESSAGE = "Database error occurred while trying to open activity. Please try again later.";
    private final static String NOT_FOUND_MESSAGE = "Activity was not found.";
    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityOpenServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String logHeader = "session:" + session.getId() + ", username:" + ((UserDTO) session.getAttribute("user")).getUsername() + ". doPost -> ";
        try {
            ActivityService activityService = new ActivityService();
            String id = request.getParameter("id");
            ActivityDTO activityDTO = new ActivityDTO();
            activityDTO.setId(Integer.parseInt(id));
            activityService.open(activityDTO);
            session.setAttribute("successMessage", SUCCESS_MESSAGE);
            LOGGER.info(logHeader + "Successfully complete.");
        } catch (ActivityOpenException e) {
            LOGGER.error(logHeader + "ActivityOpenException: " + e.getMessage());
            session.setAttribute("errorMessage", ERROR_MESSAGE);
        } catch (DBException e) {
            LOGGER.error(logHeader + "DBException: " + e.getMessage());
            session.setAttribute("warningMessage", WARNING_MESSAGE);
        } catch (ObjectNotFoundException e) {
            LOGGER.error(logHeader + "ObjectNotFoundException: " + e.getMessage());
            session.setAttribute("warningMessage", NOT_FOUND_MESSAGE);
        }
        response.sendRedirect(getServletContext().getContextPath() + "/activities");
    }

}
