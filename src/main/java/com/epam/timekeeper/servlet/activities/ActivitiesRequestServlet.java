package com.epam.timekeeper.servlet.activities;

import com.epam.timekeeper.dto.ActivityDTO;
import com.epam.timekeeper.dto.UserDTO;
import com.epam.timekeeper.dto.UserHasActivityDTO;
import com.epam.timekeeper.exception.AlreadyExistsException;
import com.epam.timekeeper.exception.DBException;
import com.epam.timekeeper.exception.DTOConversionException;
import com.epam.timekeeper.service.ActivityService;
import com.epam.timekeeper.service.UserHasActivityService;
import com.epam.timekeeper.servlet.util.SessionToRequestMessageMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "ActivitiesRequestServlet", value = "/activities/request")
public class ActivitiesRequestServlet extends HttpServlet {

    private final static String ERROR_MESSAGE = "Internal server error occurred while trying to access activities. Please try again later.";
    private final static String WARNING_MESSAGE = "Database error occurred. Please try again later.";
    private final static String ALREADY_EXISTS_MESSAGE = "Request already exists!";
    private final static String SUCCESS_REQUEST_MESSAGE = "Request successfully created!";
    private static final Logger LOGGER = LoggerFactory.getLogger(ActivitiesRequestServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionToRequestMessageMapper.map(request);
        HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute("user");
        String logHeader = "session:" + session.getId() + ", username:" + user.getUsername() + ". doGet -> ";
        try {
            ActivityService activityService = new ActivityService();
            request.setAttribute("list", activityService.getFreeForUser(user));
            request.getRequestDispatcher("/view/activities/request-activities.jsp").forward(request, response);
            LOGGER.info(logHeader + "Successfully complete.");
        } catch (DBException e) {
            LOGGER.error(logHeader + "DBException: " + e.getMessage());
            session.setAttribute("warningMessage", WARNING_MESSAGE);
            response.sendRedirect(getServletContext().getContextPath() + "/home");
        } catch (DTOConversionException e) {
            LOGGER.error(logHeader + "DTOConversionException: " + e.getMessage());
            session.setAttribute("errorMessage", ERROR_MESSAGE);
            response.sendRedirect(getServletContext().getContextPath() + "/home");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserHasActivityService userHasActivityService = new UserHasActivityService();
        HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute("user");
        String activityId = request.getParameter("id");
        String logHeader = "session:" + session.getId() + ", username:" + user.getUsername() + ". doPost -> ";
        try {
            userHasActivityService.requestActivity(createDTO(user, Integer.parseInt(activityId)));
            session.setAttribute("successMessage", SUCCESS_REQUEST_MESSAGE);
            LOGGER.info(logHeader + "Successfully complete.");
        } catch (AlreadyExistsException e) {
            LOGGER.error(logHeader + "AlreadyExistsException: " + e.getMessage());
            session.setAttribute("errorMessage", ALREADY_EXISTS_MESSAGE);
        } catch (DBException e) {
            LOGGER.error(logHeader + "DBException: " + e.getMessage());
            session.setAttribute("warningMessage", WARNING_MESSAGE);
        }
        response.sendRedirect("request");
    }

    private UserHasActivityDTO createDTO(UserDTO user, int activityId) {
        UserHasActivityDTO userHasActivity = new UserHasActivityDTO();
        ActivityDTO activity = new ActivityDTO();
        userHasActivity.setUser(user);
        activity.setId(activityId);
        userHasActivity.setActivity(activity);
        return userHasActivity;
    }

}
