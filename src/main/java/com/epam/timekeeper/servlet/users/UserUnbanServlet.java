package com.epam.timekeeper.servlet.users;

import com.epam.timekeeper.dto.UserDTO;
import com.epam.timekeeper.exception.DBException;
import com.epam.timekeeper.exception.ObjectNotFoundException;
import com.epam.timekeeper.service.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "UserUnbanServlet", value = "/users/unban")
public class UserUnbanServlet extends HttpServlet {

    private final static String SUCCESS_MESSAGE = "User successfully unbanned.";
    private final static String WARNING_MESSAGE = "Database error occurred while trying to unban user. Please try again later.";
    private final static String NOT_FOUND_MESSAGE = "User was not found.";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        try {
            UserService userService = new UserService();
            String id = request.getParameter("id");
            UserDTO user = new UserDTO();
            user.setId(Integer.parseInt(id));
            userService.unban(user);
            session.setAttribute("successMessage", SUCCESS_MESSAGE);
        } catch (DBException e) {
            e.printStackTrace();
            session.setAttribute("warningMessage", WARNING_MESSAGE);
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
            session.setAttribute("warningMessage", NOT_FOUND_MESSAGE);
        }
        response.sendRedirect(getServletContext().getContextPath() + "/users");
    }

}
