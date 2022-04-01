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
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.timekeeper.servlet.util.constants.Messages.Activities.*;
import static com.epam.timekeeper.servlet.util.constants.ServletUrn.*;
import static com.epam.timekeeper.servlet.util.constants.JspUrn.*;

/**
 * Shows activities for worker to be requested and processes requests.
 */
@WebServlet(name = "ActivitiesRequestServlet", value = ACTIVITIES_REQUEST)
public class ActivitiesRequestServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivitiesRequestServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionToRequestMessageMapper.map(request);
        HttpSession session = request.getSession();
        String lang = Arrays.stream(request.getCookies()).filter(c -> c.getName().equals("lang")).toList().get(0).getValue();
        UserDTO user = (UserDTO) session.getAttribute("user");
        String logHeader = "session:" + session.getId() + ", username:" + user.getUsername() + ". doGet -> ";
        try {
            ActivityService activityService = new ActivityService();
            request.setAttribute("list", activityService.getFreeForUser(user, lang));
            request.getRequestDispatcher(REQUEST_ACTIVITIES_JSP).forward(request, response);
            LOGGER.info(logHeader + "Successfully complete.");
        } catch (DBException e) {
            LOGGER.error(logHeader + "DBException: " + e.getMessage());
            if(lang.equals("en")) {
                session.setAttribute("warningMessage", DB_EXCEPTION_MESSAGE);
            } else {
                session.setAttribute("warningMessage", DB_EXCEPTION_MESSAGE_UA);
            }
            response.sendRedirect(getServletContext().getContextPath() + HOME);

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
        if (request.getParameter("lang") == null) {
            UserHasActivityService userHasActivityService = new UserHasActivityService();
            HttpSession session = request.getSession();
            String lang = Arrays.stream(request.getCookies()).filter(c -> c.getName().equals("lang")).toList().get(0).getValue();
            UserDTO user = (UserDTO) session.getAttribute("user");
            String activityId = request.getParameter("id");
            String logHeader = "session:" + session.getId() + ", username:" + user.getUsername() + ". doPost -> ";
            try {
                userHasActivityService.requestActivity(createDTO(user, Integer.parseInt(activityId)));
                LOGGER.info(logHeader + "Successfully complete.");
                if(lang.equals("en")) {
                    session.setAttribute("successMessage", SUCCESS_REQUEST_MESSAGE);
                } else {
                    session.setAttribute("successMessage", SUCCESS_REQUEST_MESSAGE_UA);
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
        }
        response.sendRedirect(getServletContext().getContextPath() + ACTIVITIES_REQUEST);
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
