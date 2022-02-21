package com.epam.timekeeper.servlet.categories;

import com.epam.timekeeper.dto.CategoryDTO;
import com.epam.timekeeper.dto.UserDTO;
import com.epam.timekeeper.exception.DBException;
import com.epam.timekeeper.exception.ObjectNotFoundException;
import com.epam.timekeeper.service.CategoryService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "CategoryOpenServlet", value = "/categories/open")
public class CategoryOpenServlet extends HttpServlet {

    private final static String SUCCESS_MESSAGE = "Category successfully opened.";
    private final static String WARNING_MESSAGE = "Database error occurred while trying to open category. Please try again later.";
    private final static String NOT_FOUND_MESSAGE = "Category was not found.";
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryOpenServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String logHeader = "session:" + session.getId() + ", username:" + ((UserDTO) session.getAttribute("user")).getUsername() + ". doPost -> ";
        try {
            CategoryService categoryService = new CategoryService();
            String id = request.getParameter("id");
            CategoryDTO category = new CategoryDTO();
            category.setId(Integer.parseInt(id));
            categoryService.open(category);
            session.setAttribute("successMessage", SUCCESS_MESSAGE);
            LOGGER.info(logHeader + "Successfully complete.");
        } catch (DBException e) {
            LOGGER.error(logHeader + "DBException: " + e.getMessage());
            session.setAttribute("warningMessage", WARNING_MESSAGE);
        } catch (ObjectNotFoundException e) {
            LOGGER.error(logHeader + "ObjectNotFoundException: " + e.getMessage());
            session.setAttribute("warningMessage", NOT_FOUND_MESSAGE);
        }
        response.sendRedirect(getServletContext().getContextPath() + "/categories");
    }

}
