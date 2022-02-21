package com.epam.timekeeper.servlet.activities;

import com.epam.timekeeper.dto.ActivityDTO;
import com.epam.timekeeper.dto.CategoryDTO;
import com.epam.timekeeper.dto.UserDTO;
import com.epam.timekeeper.exception.AlreadyExistsException;
import com.epam.timekeeper.exception.DBException;
import com.epam.timekeeper.service.ActivityService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "ActivityCreateServlet", value = "/activities/create")
public class ActivityCreateServlet extends HttpServlet {

    private final static String SUCCESS_MESSAGE = "Activity successfully created!";
    private final static String ALREADY_EXISTS_MESSAGE = "Activity with this name already exists!";
    private final static String WARNING_MESSAGE = "Database error occurred while trying to create activity. Please try again later.";
    private final static String REQUIREMENTS_MESSAGE = "Activity doesn't match requirements. Please try again.";

    private final static String ACTIVITY_NAME_REGEX = "^[\\sa-zA-Z0-9/.-]{8,45}$";

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityCreateServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("categoryId"));
        String name = request.getParameter("activityName");
        HttpSession session = request.getSession();
        String logHeader = "session:" + session.getId() + ", username:" + ((UserDTO) session.getAttribute("user")).getUsername() + ". doPost -> ";
        if (name.matches(ACTIVITY_NAME_REGEX)) {
            try {
                ActivityService activityService = new ActivityService();
                activityService.create(createDTO(id, name));
                session.setAttribute("successMessage", SUCCESS_MESSAGE);
                LOGGER.info(logHeader + "Successfully complete.");
            } catch (AlreadyExistsException e) {
                LOGGER.error(logHeader + "AlreadyExistsException: " + e.getMessage());
                session.setAttribute("errorMessage", ALREADY_EXISTS_MESSAGE);
            } catch (DBException e) {
                LOGGER.error(logHeader + "DBException: " + e.getMessage());
                session.setAttribute("warningMessage", WARNING_MESSAGE);
            }
        } else {
            LOGGER.error(logHeader + "Passed data doesn't meet the requirements of activity name: " + ACTIVITY_NAME_REGEX);
            session.setAttribute("errorMessage", REQUIREMENTS_MESSAGE);
        }
        response.sendRedirect(getServletContext().getContextPath() + "/activities");
    }

    private ActivityDTO createDTO(int id, String name) {
        ActivityDTO activityDTO = new ActivityDTO();
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(id);
        activityDTO.setCategory(categoryDTO);
        activityDTO.setName(name);
        return activityDTO;
    }
}
