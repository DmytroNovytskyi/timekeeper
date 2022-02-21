package com.epam.timekeeper.servlet.other;

import com.epam.timekeeper.dto.UserDTO;
import com.epam.timekeeper.servlet.util.SessionToRequestMessageMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "HomeServlet", value = "/home")
public class HomeServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDTO user = (UserDTO) request.getSession().getAttribute("user");
        SessionToRequestMessageMapper.map(request);
        String logHeader = "session:" + request.getSession().getId() + ", username:" + user.getUsername() + ". doGet -> ";
        if (user.getRole().getName().equals("ADMIN")) {
            request.getRequestDispatcher("/view/other/admin-home.jsp").forward(request, response);
        } else if (user.getRole().getName().equals("WORKER")) {
            request.getRequestDispatcher("/view/other/worker-home.jsp").forward(request, response);
        } else {
            LOGGER.error(logHeader + "Wrong role name.");
            request.getRequestDispatcher("/view/error/not-found.jsp").forward(request, response);
        }
    }

}
