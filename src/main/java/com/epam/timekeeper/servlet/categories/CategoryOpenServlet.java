package com.epam.timekeeper.servlet.categories;

import com.epam.timekeeper.dto.CategoryDTO;
import com.epam.timekeeper.exception.DBException;
import com.epam.timekeeper.exception.ObjectNotFoundException;
import com.epam.timekeeper.service.CategoryService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CategoryOpenServlet", value = "/categories/open")
public class CategoryOpenServlet extends HttpServlet {

    private final static String SUCCESS_MESSAGE = "Category successfully opened.";
    private final static String WARNING_MESSAGE = "Database error occurred while trying to open category. Please try again later.";
    private final static String NOT_FOUND_MESSAGE = "Category was not found.";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        try {
            CategoryService categoryService = new CategoryService();
            String id = request.getParameter("id");
            CategoryDTO category = new CategoryDTO();
            category.setId(Integer.parseInt(id));
            categoryService.open(category);
            session.setAttribute("successMessage", SUCCESS_MESSAGE);
        } catch (DBException e) {
            e.printStackTrace();
            session.setAttribute("warningMessage", WARNING_MESSAGE);
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
            session.setAttribute("warningMessage", NOT_FOUND_MESSAGE);
        }
        response.sendRedirect(getServletContext().getContextPath() + "/categories");
    }

}
