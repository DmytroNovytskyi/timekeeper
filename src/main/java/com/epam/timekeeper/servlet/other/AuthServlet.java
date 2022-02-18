package com.epam.timekeeper.servlet.other;

import com.epam.timekeeper.dto.UserDTO;
import com.epam.timekeeper.exception.DBException;
import com.epam.timekeeper.exception.DTOConversionException;
import com.epam.timekeeper.service.UserService;
import com.epam.timekeeper.servlet.util.SessionToRequestMessageMapper;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "AuthServlet", urlPatterns = "/auth")
public class AuthServlet extends HttpServlet {

    private final static String WRONG_DATA_MESSAGE = "Wrong username or password!";
    private final static String INTERNAL_ERROR_MESSAGE = "Internal server error occurred. Please try again later.";
    private final static String WARNING_MESSAGE = "Database error occurred while trying to validate your data. Please try again later.";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionToRequestMessageMapper.map(request);
        request.getRequestDispatcher("view/other/auth.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService userService = new UserService();
        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        try {
            UserDTO user = new UserDTO();
            user.setUsername(username);
            user = userService.validate(user, password);
            if (user != null) {
                session.setAttribute("user", user);
                response.sendRedirect("home");
            } else {
                session.setAttribute("errorMessage", WRONG_DATA_MESSAGE);
                response.sendRedirect("auth");
            }
        } catch (DBException e) {
            e.printStackTrace();
            session.setAttribute("warningMessage", WARNING_MESSAGE);
            response.sendRedirect("auth");
        } catch (DTOConversionException e) {
            e.printStackTrace();
            session.setAttribute("warningMessage", INTERNAL_ERROR_MESSAGE);
            response.sendRedirect("auth");
        }
    }

}