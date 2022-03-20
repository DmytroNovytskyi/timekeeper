package com.epam.timekeeper.servlet.activities;

import com.epam.timekeeper.dto.UserDTO;
import com.epam.timekeeper.exception.DBException;
import com.epam.timekeeper.exception.DTOConversionException;
import com.epam.timekeeper.service.ActivityService;
import com.epam.timekeeper.service.CategoryService;
import com.epam.timekeeper.service.UserHasActivityService;
import com.epam.timekeeper.servlet.util.SessionToRequestMessageMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.timekeeper.servlet.util.constants.Messages.Activities.*;
import static com.epam.timekeeper.servlet.util.constants.ServletUrn.*;
import static com.epam.timekeeper.servlet.util.constants.JspUrn.*;

@WebServlet(name = "ActivitiesServlet", value = ACTIVITIES)
public class ActivitiesServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivitiesServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDTO user = (UserDTO) request.getSession().getAttribute("user");
        SessionToRequestMessageMapper.map(request);
        HttpSession session = request.getSession();
        String logHeader = "session:" + session.getId() + ", username:" + user.getUsername() + ". doGet -> ";
        try {
            if (user.getRole().getName().equals("ADMIN")) {
                ActivityService activityService = new ActivityService();
                CategoryService categoryService = new CategoryService();
                request.setAttribute("activities", activityService.getAll());
                request.setAttribute("categories", categoryService.getAllOpened());
                request.getRequestDispatcher(ADMIN_ACTIVITIES_JSP).forward(request, response);
            } else {
                UserHasActivityService userHasActivityService = new UserHasActivityService();
                request.setAttribute("list", userHasActivityService.getActiveForUser(user));
                request.getRequestDispatcher(WORKER_ACTIVITIES_JSP).forward(request, response);
            }
            LOGGER.info(logHeader + "Successfully complete.");
        } catch (DBException e) {
            LOGGER.error(logHeader + "DBException: " + e.getMessage());
            session.setAttribute("warningMessage", DB_EXCEPTION_MESSAGE);
            response.sendRedirect(getServletContext().getContextPath() + HOME);
        } catch (DTOConversionException e) {
            LOGGER.error(logHeader + "DTOConversionException: " + e.getMessage());
            session.setAttribute("errorMessage", DTO_CONVERSION_MESSAGE);
            response.sendRedirect(getServletContext().getContextPath() + HOME);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(getServletContext().getContextPath() + ACTIVITIES);
    }
}
