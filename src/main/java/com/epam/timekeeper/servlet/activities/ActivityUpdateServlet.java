package com.epam.timekeeper.servlet.activities;

import com.epam.timekeeper.dto.ActivityDTO;
import com.epam.timekeeper.dto.CategoryDTO;
import com.epam.timekeeper.dto.UserDTO;
import com.epam.timekeeper.exception.AlreadyExistsException;
import com.epam.timekeeper.exception.DBException;
import com.epam.timekeeper.exception.ObjectNotFoundException;
import com.epam.timekeeper.service.ActivityService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.timekeeper.servlet.util.constants.Messages.Activities.*;
import static com.epam.timekeeper.servlet.util.constants.ServletUrn.*;

@WebServlet(name = "ActivityUpdateServlet", value = ACTIVITIES_UPDATE)
public class ActivityUpdateServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityUpdateServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int activityId = Integer.parseInt(request.getParameter("activityId"));
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        String activityName = request.getParameter("activityName");
        HttpSession session = request.getSession();
        String logHeader = "session:" + session.getId() + ", username:"
                + ((UserDTO) session.getAttribute("user")).getUsername() + ". doPost -> ";
        if (activityName.matches(ACTIVITY_NAME_REGEX)) {
            try {
                ActivityService activityService = new ActivityService();
                activityService.update(createDTO(activityId, categoryId, activityName));
                session.setAttribute("successMessage", SUCCESS_UPDATE_MESSAGE);
                LOGGER.info(logHeader + "Successfully complete.");
            } catch (AlreadyExistsException e) {
                LOGGER.error(logHeader + "AlreadyExistsException: " + e.getMessage());
                session.setAttribute("errorMessage", ACTIVITY_ALREADY_EXISTS_MESSAGE);
            } catch (DBException e) {
                LOGGER.error(logHeader + "DBException: " + e.getMessage());
                session.setAttribute("warningMessage", DB_EXCEPTION_MESSAGE);
            } catch (ObjectNotFoundException e) {
                LOGGER.error(logHeader + "ObjectNotFoundException: " + e.getMessage());
                session.setAttribute("warningMessage", NOT_FOUND_MESSAGE);
            }
        } else {
            LOGGER.error(logHeader + "Passed data doesn't meet the requirements of activity name: " + ACTIVITY_NAME_REGEX);
            session.setAttribute("errorMessage", REQUIREMENTS_MESSAGE);
        }
        response.sendRedirect(getServletContext().getContextPath() + ACTIVITIES);
    }

    private ActivityDTO createDTO(int activityId, int categoryId, String activityName) {
        ActivityDTO activityDTO = new ActivityDTO();
        CategoryDTO categoryDTO = new CategoryDTO();
        activityDTO.setId(activityId);
        categoryDTO.setId(categoryId);
        activityDTO.setCategory(categoryDTO);
        activityDTO.setName(activityName);
        return activityDTO;
    }
}
