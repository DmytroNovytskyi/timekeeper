package com.epam.timekeeper.servlet.users;

import com.epam.timekeeper.exception.DBException;
import com.epam.timekeeper.exception.DTOConversionException;
import com.epam.timekeeper.service.UserService;
import com.epam.timekeeper.servlet.util.SessionToRequestMessageMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "UsersServlet", value = "/users")
public class UsersServlet extends HttpServlet {

    private final static String ERROR_MESSAGE = "Internal server error occurred while trying to access categories. Please try again later.";
    private final static String WARNING_MESSAGE = "Database error occurred while trying to access categories. Please try again later.";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionToRequestMessageMapper.map(request);
        HttpSession session = request.getSession();
        try {
            UserService userService = new UserService();
            request.setAttribute("list", userService.getAll());
            request.getRequestDispatcher("/view/users/users.jsp").forward(request, response);
        } catch (DBException e) {
            e.printStackTrace();
            session.setAttribute("warningMessage", WARNING_MESSAGE);
            response.sendRedirect("home");
        } catch (DTOConversionException e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", ERROR_MESSAGE);
            response.sendRedirect("home");
        }
    }
}
