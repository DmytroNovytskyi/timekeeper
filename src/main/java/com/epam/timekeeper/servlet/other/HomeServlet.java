package com.epam.timekeeper.servlet.other;

import com.epam.timekeeper.dto.UserDTO;
import com.epam.timekeeper.servlet.util.SessionToRequestMessageMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.timekeeper.servlet.util.constants.ServletUrn.*;
import static com.epam.timekeeper.servlet.util.constants.JspUrn.*;

/**
 * Shows home page for admin and worker.
 */
@WebServlet(name = "HomeServlet", value = HOME)
public class HomeServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDTO user = (UserDTO) request.getSession().getAttribute("user");
        SessionToRequestMessageMapper.map(request);
        String logHeader = "session:" + request.getSession().getId() + ", username:" + user.getUsername() + ". doGet -> ";
        if (user.getRole().getName().equals("ADMIN")) {
            request.getRequestDispatcher(ADMIN_HOME_JSP).forward(request, response);
        } else if (user.getRole().getName().equals("WORKER")) {
            request.getRequestDispatcher(WORKER_HOME_JSP).forward(request, response);
        } else {
            LOGGER.error(logHeader + "Wrong role name.");
            request.getRequestDispatcher(NOT_FOUND_JSP).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(getServletContext().getContextPath() + HOME);
    }

}
