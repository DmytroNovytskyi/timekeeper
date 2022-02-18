package com.epam.timekeeper.servlet.users;

import com.epam.timekeeper.dto.RoleDTO;
import com.epam.timekeeper.dto.UserDTO;
import com.epam.timekeeper.exception.AlreadyExistsException;
import com.epam.timekeeper.exception.DBException;
import com.epam.timekeeper.exception.DTOConversionException;
import com.epam.timekeeper.service.ActivityService;
import com.epam.timekeeper.service.RoleService;
import com.epam.timekeeper.service.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "NewUserServlet", value = "/users/new")
public class NewUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RoleService roleService = new RoleService();
        HttpSession session = request.getSession();
        request.setAttribute("message", session.getAttribute("message"));
        session.removeAttribute("message");
        try {
            List<RoleDTO> roles = roleService.getAll();
            request.setAttribute("list", roles);
            request.getRequestDispatcher("/view/users/new-user.jsp").forward(request, response);
        } catch (DBException e) {
            e.printStackTrace();
            request.getRequestDispatcher("/view/error/db-error.jsp").forward(request, response);
        } catch (DTOConversionException e) {
            e.printStackTrace();
            request.getRequestDispatcher("/view/error/something-went-wrong.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String SUCCESS_MESSAGE = "User successfully created!";
        String ALREADY_EXISTS_MESSAGE = "User with this name or email already exists!";
        HttpSession session = request.getSession();
        try {
            UserService userService = new UserService();
            int roleId = Integer.parseInt(request.getParameter("roleId"));
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            userService.create(createDTO(roleId, username, email), password);
            request.getSession().setAttribute("message", SUCCESS_MESSAGE);
            response.sendRedirect(getServletContext().getContextPath() + "/users");
        } catch (AlreadyExistsException e) {
            e.printStackTrace();
            session.setAttribute("message", ALREADY_EXISTS_MESSAGE);
            response.sendRedirect("new");
        } catch (DBException e) {
            e.printStackTrace();
            request.getRequestDispatcher("/view/error/db-error.jsp").forward(request, response);
        }
    }

    private UserDTO createDTO(int roleId, String username, String email){
        UserDTO user = new UserDTO();
        RoleDTO role = new RoleDTO();
        role.setId(roleId);
        user.setRole(role);
        user.setUsername(username);
        user.setEmail(email);
        return user;

    }
}
