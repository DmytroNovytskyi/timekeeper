package com.epam.timekeeper.servlet.activities;

import com.epam.timekeeper.dto.ActivityDTO;
import com.epam.timekeeper.exception.DBException;
import com.epam.timekeeper.exception.ObjectNotFoundException;
import com.epam.timekeeper.service.ActivityService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ActivityCloseServlet", value = "/activities/close")
public class ActivityCloseServlet extends HttpServlet {

    private final static String SUCCESS_MESSAGE = "Activity successfully closed.";
    private final static String WARNING_MESSAGE = "Database error occurred while trying to close activity. Please try again later.";
    private final static String NOT_FOUND_MESSAGE = "Activity was not found.";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        try {
            ActivityService activityService = new ActivityService();
            String id = request.getParameter("id");
            ActivityDTO activityDTO = new ActivityDTO();
            activityDTO.setId(Integer.parseInt(id));
            activityService.close(activityDTO);
            session.setAttribute("successMessage", SUCCESS_MESSAGE);
        } catch (DBException e) {
            e.printStackTrace();
            session.setAttribute("warningMessage", WARNING_MESSAGE);
        } catch (ObjectNotFoundException e){
            e.printStackTrace();
            session.setAttribute("warningMessage", NOT_FOUND_MESSAGE);
        }
        response.sendRedirect(getServletContext().getContextPath() + "/activities");
    }

}
