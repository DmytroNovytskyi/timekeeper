package com.epam.timekeeper.servlet.requests;

import com.epam.timekeeper.dto.UserDTO;
import com.epam.timekeeper.dto.UserHasActivityDTO;
import com.epam.timekeeper.exception.DBException;
import com.epam.timekeeper.service.UserHasActivityService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.timekeeper.servlet.util.constants.Messages.Requests.*;
import static com.epam.timekeeper.servlet.util.constants.ServletUrn.*;

/**
 * Processes admin action to the requests created by worker.
 */
@WebServlet(name = "ProcessRequestServlet", value = PROCESS_REQUEST)
public class ProcessRequestServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessRequestServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String lang = Arrays.stream(request.getCookies()).filter(c -> c.getName().equals("lang")).toList().get(0).getValue();
        UserHasActivityService userHasActivityService = new UserHasActivityService();
        String type = request.getParameter("type");
        UserDTO user = (UserDTO) session.getAttribute("user");
        String logHeader = "session:" + session.getId() + ", username:" + user.getUsername() + ". doPost -> ";
        try {
            UserHasActivityDTO userHasActivity = new UserHasActivityDTO();
            userHasActivity.setId(Integer.parseInt(request.getParameter("id")));
            if (user.getRole().getName().equals("ADMIN")) {
                String action = request.getParameter("action");
                if (action.equals("approve") && type.equals("assign")) {
                    userHasActivityService.approveAssign(userHasActivity);
                    LOGGER.info(logHeader + "Approve assign successfully complete.");
                } else if (action.equals("approve") && type.equals("abort")) {
                    userHasActivityService.approveAbort(userHasActivity);
                    LOGGER.info(logHeader + "Approve abort successfully complete.");
                } else if (action.equals("decline") && type.equals("assign")) {
                    userHasActivityService.declineAssign(userHasActivity);
                    LOGGER.info(logHeader + "Decline assign successfully complete.");
                } else if (action.equals("decline") && type.equals("abort")) {
                    userHasActivityService.declineAbort(userHasActivity);
                    LOGGER.info(logHeader + "Decline abort successfully complete.");
                }
            } else {
                if (type.equals("assign")) {
                    userHasActivityService.cancelAssign(userHasActivity);
                    LOGGER.info(logHeader + "Cancel assign successfully complete.");
                } else if (type.equals("abort")) {
                    userHasActivityService.cancelAbort(userHasActivity);
                    LOGGER.info(logHeader + "Cancel abort successfully complete.");
                }
            }
            if(lang.equals("en")) {
                session.setAttribute("successMessage", SUCCESS_PROCESS_MESSAGE);
            } else {
                session.setAttribute("successMessage", SUCCESS_PROCESS_MESSAGE_UA);
            }
        } catch (DBException e) {
            LOGGER.error(logHeader + "DBException: " + e.getMessage());
            if(lang.equals("en")) {
                session.setAttribute("warningMessage", DB_EXCEPTION_MESSAGE);
            } else {
                session.setAttribute("warningMessage", DB_EXCEPTION_MESSAGE_UA);
            }
        }
        response.sendRedirect(getServletContext().getContextPath() + REQUESTS);
    }

}
