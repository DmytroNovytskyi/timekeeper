package com.epam.timekeeper.servlet.other;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import com.epam.timekeeper.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "QuitServlet", urlPatterns = "/quit")
public class QuitServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuitServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String logHeader = "session:" + session.getId() + ", username:" + ((UserDTO) session.getAttribute("user")).getUsername() + ". doPost -> ";
        request.getSession().removeAttribute("user");
        response.sendRedirect("auth");
        LOGGER.info(logHeader + "Successfully logged out.");
    }

}
