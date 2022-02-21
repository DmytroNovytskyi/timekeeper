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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "RequestsServlet", value = "/requests")
public class RequestsServlet extends HttpServlet {

    private final static String ERROR_MESSAGE = "Internal server error occurred while trying to access categories. Please try again later.";
    private final static String WARNING_MESSAGE = "Database error occurred while trying to access categories. Please try again later.";
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestsServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionToRequestMessageMapper.map(request);
        HttpSession session = request.getSession();
        String logHeader = "session:" + session.getId() + ", username:" + ((UserDTO) session.getAttribute("user")).getUsername() + ". doGet -> ";
        try {
            UserHasActivityService userHasActivityService = new UserHasActivityService();
            request.setAttribute("list", userHasActivityService.getAllPending());
            request.getRequestDispatcher("/view/requests/requests.jsp").forward(request, response);
            LOGGER.info(logHeader + "Successfully complete.");
        } catch (DBException e) {
            LOGGER.error(logHeader + "DBException: " + e.getMessage());
            session.setAttribute("warningMessage", WARNING_MESSAGE);
            response.sendRedirect("home");
        } catch (DTOConversionException e) {
            LOGGER.error(logHeader + "DTOConversionException: " + e.getMessage());
            session.setAttribute("errorMessage", ERROR_MESSAGE);
            response.sendRedirect("home");
        }
    }

}
