package com.epam.timekeeper.servlet.requests;

import com.epam.timekeeper.dto.UserDTO;
import com.epam.timekeeper.exception.DBException;
import com.epam.timekeeper.exception.DTOConversionException;
import com.epam.timekeeper.service.UserHasActivityService;
import com.epam.timekeeper.servlet.util.SessionToRequestMessageMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.timekeeper.servlet.util.constants.Messages.Requests.*;
import static com.epam.timekeeper.servlet.util.constants.ServletUrn.*;
import static com.epam.timekeeper.servlet.util.constants.JspUrn.*;

/**
 * Shows all pending requests for admin and only user related requests to worker.
 */
@WebServlet(name = "RequestsServlet", value = REQUESTS)
public class RequestsServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestsServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String lang = Arrays.stream(request.getCookies()).filter(c -> c.getName().equals("lang")).toList().get(0).getValue();
        SessionToRequestMessageMapper.map(request);
        HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute("user");
        String logHeader = "session:" + session.getId() + ", username:" + user.getUsername() + ". doGet -> ";
        try {
            UserHasActivityService userHasActivityService = new UserHasActivityService();
            if (user.getRole().getName().equals("ADMIN")) {
                request.setAttribute("list", userHasActivityService.getAllPending(lang));
                request.getRequestDispatcher(ADMIN_REQUESTS_JSP).forward(request, response);
            } else {
                request.setAttribute("list", userHasActivityService.getPendingForUser(user, lang));
                request.getRequestDispatcher(WORKER_REQUESTS_JSP).forward(request, response);
            }
            LOGGER.info(logHeader + "Successfully complete.");
        } catch (DBException e) {
            LOGGER.error(logHeader + "DBException: " + e.getMessage());
            if(lang.equals("en")) {
                session.setAttribute("warningMessage", DB_EXCEPTION_MESSAGE);
            } else {
                session.setAttribute("warningMessage", DB_EXCEPTION_MESSAGE_UA);
            }
            response.sendRedirect(getServletContext().getContextPath() + HOME);
        } catch (DTOConversionException e) {
            LOGGER.error(logHeader + "DTOConversionException: " + e.getMessage());
            if(lang.equals("en")) {
                session.setAttribute("errorMessage", DTO_CONVERSION_MESSAGE);
            } else {
                session.setAttribute("errorMessage", DTO_CONVERSION_MESSAGE_UA);
            }
            response.sendRedirect(getServletContext().getContextPath() + HOME);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(getServletContext().getContextPath() + REQUESTS);
    }
}
