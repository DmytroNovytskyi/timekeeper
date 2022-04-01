package com.epam.timekeeper.servlet.activities;

import com.epam.timekeeper.dto.ActivityDTO;
import com.epam.timekeeper.dto.UserDTO;
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
 * Closes activity and redirects to activities page. Admin only.
 */
@WebServlet(name = "ActivityCloseServlet", value = ACTIVITIES_CLOSE)
public class ActivityCloseServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityCloseServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String lang = Arrays.stream(request.getCookies()).filter(c -> c.getName().equals("lang")).toList().get(0).getValue();
        String logHeader = "session:" + session.getId() + ", username:"
                + ((UserDTO) session.getAttribute("user")).getUsername() + ". doPost -> ";
        try {
            ActivityService activityService = new ActivityService();
            String id = request.getParameter("id");
            ActivityDTO activityDTO = new ActivityDTO();
            activityDTO.setId(Integer.parseInt(id));
            activityService.close(activityDTO);
            if(lang.equals("en")) {
                session.setAttribute("successMessage", SUCCESS_CLOSE_MESSAGE);
            } else {
                session.setAttribute("successMessage", SUCCESS_CLOSE_MESSAGE_UA);
            }
            LOGGER.info(logHeader + "Successfully complete.");
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
        response.sendRedirect(getServletContext().getContextPath() + ACTIVITIES);
    }

}
