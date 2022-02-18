package com.epam.timekeeper.servlet.other;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "QuitServlet", urlPatterns = "/quit")
public class QuitServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().removeAttribute("user");
        response.sendRedirect("auth");
    }

}
