package com.epam.timekeeper.servlet.categories;

import com.epam.timekeeper.dto.CategoryDTO;
import com.epam.timekeeper.exception.DBException;
import com.epam.timekeeper.exception.DTOConversionException;
import com.epam.timekeeper.service.CategoryService;
import com.epam.timekeeper.servlet.util.SessionToRequestMessageMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CategoriesServlet", value = "/categories")
public class CategoriesServlet extends HttpServlet {

    private final static String ERROR_MESSAGE = "Internal server error occurred while trying to access categories. Please try again later.";
    private final static String WARNING_MESSAGE = "Database error occurred while trying to access categories. Please try again later.";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionToRequestMessageMapper.map(request);
        HttpSession session = request.getSession();
        try {
            CategoryService categoryService = new CategoryService();
            request.setAttribute("list", categoryService.getAll());
            request.getRequestDispatcher("/view/categories/categories.jsp").forward(request, response);
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
