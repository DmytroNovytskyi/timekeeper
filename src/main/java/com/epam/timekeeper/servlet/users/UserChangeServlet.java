package com.epam.timekeeper.servlet.users;

import com.epam.timekeeper.dto.RoleDTO;
import com.epam.timekeeper.dto.UserDTO;
import com.epam.timekeeper.entity.User;
import com.epam.timekeeper.exception.AlreadyExistsException;
import com.epam.timekeeper.exception.DBException;
import com.epam.timekeeper.exception.DTOConversionException;
import com.epam.timekeeper.service.RoleService;
import com.epam.timekeeper.service.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "UserChangeServlet", value = "/users/change")
public class UserChangeServlet extends HttpServlet {

    private final UserService userService = new UserService();
    private final RoleService roleService = new RoleService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String parameter = request.getParameter("id");
        if (parameter == null) {
            response.sendRedirect(getServletContext().getContextPath() + "/users");
        } else {
            HttpSession session = request.getSession();
            request.setAttribute("message", session.getAttribute("message"));
            session.removeAttribute("message");
            try {
                UserDTO user = new UserDTO();
                user.setId(Integer.parseInt(parameter));
                user = userService.get(user);
                if (user != null) {
                    request.setAttribute("user", user);
                    request.getRequestDispatcher("/view/users/change-user.jsp").forward(request, response);
                } else {
                    request.getRequestDispatcher("/view/error/something-went-wrong.jsp").forward(request, response);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                response.sendRedirect(getServletContext().getContextPath() + "/users");
            } catch (DBException e) {
                e.printStackTrace();
                request.getRequestDispatcher("/view/error/db-error.jsp").forward(request, response);
            } catch (DTOConversionException e) {
                e.printStackTrace();
                request.getRequestDispatcher("/view/error/something-went-wrong.jsp").forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String SUCCESS_MESSAGE = "User successfully updated!";
        String ALREADY_EXISTS_MESSAGE = "User with this name or email already exists!";
        int userId = Integer.parseInt(request.getParameter("userId"));
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();
        try {
            userService.update(createDTO(userId, username, email), password);
            session.setAttribute("message", SUCCESS_MESSAGE);
            response.sendRedirect(getServletContext().getContextPath() + "/users");
        } catch (AlreadyExistsException e) {
            e.printStackTrace();
            session.setAttribute("message", ALREADY_EXISTS_MESSAGE);
            response.sendRedirect("change?id=" + userId);
        } catch (DBException e) {
            e.printStackTrace();
            request.getRequestDispatcher("/view/error/db-error.jsp").forward(request, response);
        } catch (DTOConversionException e) {
            e.printStackTrace();
            request.getRequestDispatcher("/view/error/something-went-wrong.jsp").forward(request, response);
        }
    }

    private UserDTO createDTO(int userId, String username, String email){
        UserDTO user = new UserDTO();
        user.setRole(new RoleDTO());
        user.setId(userId);
        user.setUsername(username);
        user.setEmail(email);
        return user;
    }
}
