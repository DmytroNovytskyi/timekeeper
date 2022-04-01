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
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.timekeeper.servlet.util.constants.Messages.Activities.*;
import static com.epam.timekeeper.servlet.util.constants.ServletUrn.*;

/**
 * Updates activity and redirects to activities page. Admin only.
 */
@WebServlet(name = "ActivityUpdateServlet", value = ACTIVITIES_UPDATE)
public class ActivityUpdateServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityUpdateServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int activityId = Integer.parseInt(request.getParameter("activityId"));
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        String description = request.getParameter("description");
        String activityName = request.getParameter("activityName");
        HttpSession session = request.getSession();
        String lang = Arrays.stream(request.getCookies()).filter(c -> c.getName().equals("lang")).toList().get(0).getValue();
        String logHeader = "session:" + session.getId() + ", username:"
                + ((UserDTO) session.getAttribute("user")).getUsername() + ". doPost -> ";
        if (activityName.matches(ACTIVITY_NAME_REGEX) && description.matches(ACTIVITY_DESCRIPTION_REGEX)) {
            try {
                ActivityService activityService = new ActivityService();
                activityService.update(createDTO(activityId, categoryId, activityName, description));
                if(lang.equals("en")) {
                    session.setAttribute("successMessage", SUCCESS_UPDATE_MESSAGE);
                } else {
                    session.setAttribute("successMessage", SUCCESS_UPDATE_MESSAGE_UA);
                }
                LOGGER.info(logHeader + "Successfully complete.");
            } catch (AlreadyExistsException e) {
                LOGGER.error(logHeader + "AlreadyExistsException: " + e.getMessage());
                if(lang.equals("en")) {
                    session.setAttribute("errorMessage", ACTIVITY_ALREADY_EXISTS_MESSAGE);
                } else {
                    session.setAttribute("errorMessage", ACTIVITY_ALREADY_EXISTS_MESSAGE_UA);
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
        } else {
            LOGGER.error(logHeader + "Passed data doesn't meet the requirements of activity name: "
                    + ACTIVITY_NAME_REGEX + " or description:" + ACTIVITY_DESCRIPTION_REGEX);
            if(lang.equals("en")) {
                session.setAttribute("errorMessage", REQUIREMENTS_MESSAGE);
            } else {
                session.setAttribute("errorMessage", REQUIREMENTS_MESSAGE_UA);
            }
        }
        response.sendRedirect(getServletContext().getContextPath() + ACTIVITIES);
    }

    private ActivityDTO createDTO(int activityId, int categoryId, String activityName, String description) {
        ActivityDTO activityDTO = new ActivityDTO();
        CategoryDTO categoryDTO = new CategoryDTO();
        activityDTO.setId(activityId);
        categoryDTO.setId(categoryId);
        activityDTO.setCategory(categoryDTO);
        activityDTO.setName(activityName);
        activityDTO.setDescription(description);
        return activityDTO;
    }
}
