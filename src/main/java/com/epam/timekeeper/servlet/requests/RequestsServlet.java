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

import static com.epam.timekeeper.servlet.util.constants.Messages.Requests.*;
import static com.epam.timekeeper.servlet.util.constants.ServletUrn.*;
import static com.epam.timekeeper.servlet.util.constants.JspUrn.*;

@WebServlet(name = "RequestsServlet", value = REQUESTS)
public class RequestsServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestsServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionToRequestMessageMapper.map(request);
        HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute("user");
        String logHeader = "session:" + session.getId() + ", username:" + user.getUsername() + ". doGet -> ";
        try {
            UserHasActivityService userHasActivityService = new UserHasActivityService();
            if (user.getRole().getName().equals("ADMIN")) {
                request.setAttribute("list", userHasActivityService.getAllPending());
                request.getRequestDispatcher(ADMIN_REQUESTS_JSP).forward(request, response);
            } else {
                request.setAttribute("list", userHasActivityService.getPendingForUser(user));
                request.getRequestDispatcher(WORKER_REQUESTS_JSP).forward(request, response);
            }
            LOGGER.info(logHeader + "Successfully complete.");
        } catch (DBException e) {
            LOGGER.error(logHeader + "DBException: " + e.getMessage());
            session.setAttribute("warningMessage", DB_EXCEPTION_MESSAGE);
            response.sendRedirect(getServletContext().getContextPath() + HOME);
        } catch (DTOConversionException e) {
            LOGGER.error(logHeader + "DTOConversionException: " + e.getMessage());
            session.setAttribute("errorMessage", DTO_CONVERSION_MESSAGE);
            response.sendRedirect(getServletContext().getContextPath() + HOME);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(getServletContext().getContextPath() + REQUESTS);
    }
}
