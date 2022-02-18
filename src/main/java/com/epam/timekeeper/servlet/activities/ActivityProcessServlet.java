package com.epam.timekeeper.servlet.activities;

import com.epam.timekeeper.dto.UserHasActivityDTO;
import com.epam.timekeeper.exception.AlreadyExistsException;
import com.epam.timekeeper.exception.DBException;
import com.epam.timekeeper.service.UserHasActivityService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ActivityProcessServlet", value = "/activities/process")
public class ActivityProcessServlet extends HttpServlet {

    private final static String SUCCESS_MESSAGE_START = "Activity successfully started!";
    private final static String SUCCESS_MESSAGE_END = "Activity successfully ended!";
    private final static String SUCCESS_MESSAGE_ABORT = "Abort successfully requested!";
    private final static String ERROR_ALREADY_EXISTS_MESSAGE = "Request already exists!";
    private final static String WARNING_MESSAGE = "Database error occurred. Please try again later.";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserHasActivityService userHasActivityService = new UserHasActivityService();
        HttpSession session = request.getSession();
        int id = Integer.parseInt(request.getParameter("id"));
        String action = request.getParameter("action");
        UserHasActivityDTO userHasActivity = new UserHasActivityDTO();
        userHasActivity.setId(id);
        try {
            switch (action) {
                case "Start" -> {
                    userHasActivityService.start(userHasActivity);
                    session.setAttribute("successMessage", SUCCESS_MESSAGE_START);
                }
                case "End" -> {
                    userHasActivityService.end(userHasActivity);
                    session.setAttribute("successMessage", SUCCESS_MESSAGE_END);
                }
                case "Abort" -> {
                    userHasActivityService.requestAbort(userHasActivity);
                    session.setAttribute("successMessage", SUCCESS_MESSAGE_ABORT);
                }
            }
        } catch (AlreadyExistsException e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", ERROR_ALREADY_EXISTS_MESSAGE);
        } catch (DBException e) {
            e.printStackTrace();
            session.setAttribute("warningMessage", WARNING_MESSAGE);
        }
        response.sendRedirect(getServletContext().getContextPath() + "/activities");
    }

}
