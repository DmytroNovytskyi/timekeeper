package com.epam.timekeeper.servlet.other;

import com.epam.timekeeper.dto.UserDTO;
import com.epam.timekeeper.servlet.util.SessionToRequestMessageMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "HomeServlet", value = "/home")
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDTO user = (UserDTO) request.getSession().getAttribute("user");
        SessionToRequestMessageMapper.map(request);
        if (user.getRole().getName().equals("ADMIN")) {
            request.getRequestDispatcher("/view/other/admin-home.jsp").forward(request, response);
        } else if (user.getRole().getName().equals("WORKER")) {
            request.getRequestDispatcher("/view/other/worker-home.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/view/error/not-found.jsp").forward(request, response);
        }
    }

}
