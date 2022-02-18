package com.epam.timekeeper.servlet.requests;

import com.epam.timekeeper.dto.UserHasActivityDTO;
import com.epam.timekeeper.exception.AlreadyExistsException;
import com.epam.timekeeper.exception.DBException;
import com.epam.timekeeper.service.UserHasActivityService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ProcessRequestServlet", value = "/requests/process")
public class ProcessRequestServlet extends HttpServlet {

    private final static String SUCCESS_MESSAGE = "Request successfully processed!";
    private final static String WARNING_MESSAGE = "Database error occurred while trying to process request. Please try again later.";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserHasActivityService userHasActivityService = new UserHasActivityService();
        String action = request.getParameter("action");
        String type = request.getParameter("type");
        UserHasActivityDTO userHasActivity = new UserHasActivityDTO();
        userHasActivity.setId(Integer.parseInt(request.getParameter("id")));
        try {
            if (action.equals("Approve") && type.equals("assign")) {
                userHasActivityService.approveAssign(userHasActivity);
            } else if (action.equals("Approve") && type.equals("abort")) {
                userHasActivityService.approveAbort(userHasActivity);
            } else if (action.equals("Decline") && type.equals("assign")) {
                userHasActivityService.declineAssign(userHasActivity);
            } else if (action.equals("Decline") && type.equals("abort")) {
                userHasActivityService.declineAbort(userHasActivity);
            }
            request.getSession().setAttribute("successMessage", SUCCESS_MESSAGE);
        } catch (DBException e) {
            e.printStackTrace();
            request.getSession().setAttribute("warningMessage", WARNING_MESSAGE);
        }
        response.sendRedirect(getServletContext().getContextPath() + "/requests");
    }

}
