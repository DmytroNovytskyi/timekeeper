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

import static com.epam.timekeeper.servlet.util.constants.Messages.Activities.*;
import static com.epam.timekeeper.servlet.util.constants.ServletUrn.*;

@WebServlet(name = "ActivityOpenServlet", value = ACTIVITIES_OPEN)
public class ActivityOpenServlet extends HttpServlet {

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
            session.setAttribute("successMessage", SUCCESS_OPEN_MESSAGE);
            LOGGER.info(logHeader + "Successfully complete.");
        } catch (ActivityOpenException e) {
            LOGGER.error(logHeader + "ActivityOpenException: " + e.getMessage());
            session.setAttribute("errorMessage", ACTIVITY_OPEN_EXCEPTION_MESSAGE);
        } catch (DBException e) {
            LOGGER.error(logHeader + "DBException: " + e.getMessage());
            session.setAttribute("warningMessage", DB_EXCEPTION_MESSAGE);
        } catch (ObjectNotFoundException e) {
            LOGGER.error(logHeader + "ObjectNotFoundException: " + e.getMessage());
            session.setAttribute("warningMessage", NOT_FOUND_MESSAGE);
        }
        response.sendRedirect(getServletContext().getContextPath() + ACTIVITIES);
    }

}
