package com.epam.timekeeper.servlet.activities;

import com.epam.timekeeper.dto.UserDTO;
import com.epam.timekeeper.exception.DBException;
import com.epam.timekeeper.exception.DTOConversionException;
import com.epam.timekeeper.service.ActivityService;
import com.epam.timekeeper.service.UserHasActivityService;
import com.epam.timekeeper.servlet.util.SessionToRequestMessageMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ActivitiesServlet", value = "/activities")
public class ActivitiesServlet extends HttpServlet {

    private final static String ERROR_MESSAGE = "Internal server error occurred while trying to access activities. Please try again later.";
    private final static String WARNING_MESSAGE = "Database error occurred while trying to access activities. Please try again later.";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDTO user = (UserDTO) request.getSession().getAttribute("user");
        SessionToRequestMessageMapper.map(request);
        HttpSession session = request.getSession();
        try {
            if (user.getRole().getName().equals("ADMIN")) {
                ActivityService activityService = new ActivityService();
                request.setAttribute("list", activityService.getAll());
                request.getRequestDispatcher("/view/activities/admin-activities.jsp").forward(request, response);
            } else {
                UserHasActivityService userHasActivityService = new UserHasActivityService();
                request.setAttribute("list", userHasActivityService.getActiveForUser(user));
                request.getRequestDispatcher("/view/activities/worker-activities.jsp").forward(request, response);
            }
        } catch (DBException e) {
            e.printStackTrace();
            session.setAttribute("warningMessage", WARNING_MESSAGE);
            response.sendRedirect("home");
        } catch (DTOConversionException e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", ERROR_MESSAGE);
            response.sendRedirect("home");
        }
    }

}
