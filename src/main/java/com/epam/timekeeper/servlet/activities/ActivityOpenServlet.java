package com.epam.timekeeper.servlet.activities;

import com.epam.timekeeper.dto.ActivityDTO;
import com.epam.timekeeper.exception.ActivityOpenException;
import com.epam.timekeeper.exception.DBException;
import com.epam.timekeeper.exception.ObjectNotFoundException;
import com.epam.timekeeper.service.ActivityService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ActivityOpenServlet", value = "/activities/open")
public class ActivityOpenServlet extends HttpServlet {

    private final static String SUCCESS_MESSAGE = "Activity successfully opened.";
    private final static String ERROR_MESSAGE = "Activity cannot be opened because category is closed.";
    private final static String WARNING_MESSAGE = "Database error occurred while trying to open activity. Please try again later.";
    private final static String NOT_FOUND_MESSAGE = "Activity was not found.";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        try {
            ActivityService activityService = new ActivityService();
            String id = request.getParameter("id");
            ActivityDTO activityDTO = new ActivityDTO();
            activityDTO.setId(Integer.parseInt(id));
            activityService.open(activityDTO);
            session.setAttribute("successMessage", SUCCESS_MESSAGE);
        } catch (ActivityOpenException e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", ERROR_MESSAGE);
        } catch (DBException e) {
            e.printStackTrace();
            session.setAttribute("warningMessage", WARNING_MESSAGE);
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
            session.setAttribute("warningMessage", NOT_FOUND_MESSAGE);
        }
        response.sendRedirect(getServletContext().getContextPath() + "/activities");
    }

}
