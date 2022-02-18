package com.epam.timekeeper.servlet.categories;

import com.epam.timekeeper.dto.CategoryDTO;
import com.epam.timekeeper.exception.AlreadyExistsException;
import com.epam.timekeeper.exception.DBException;
import com.epam.timekeeper.exception.DTOConversionException;
import com.epam.timekeeper.service.CategoryService;
import com.sun.net.httpserver.HttpPrincipal;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CategoryChangeServlet", value = "/categories/change")
public class CategoryChangeServlet extends HttpServlet {

    private final CategoryService categoryService = new CategoryService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String parameter = request.getParameter("id");
        if (parameter == null) {
            response.sendRedirect(getServletContext().getContextPath() + "/categories");
        } else {
            HttpSession session = request.getSession();
            request.setAttribute("message", session.getAttribute("message"));
            session.removeAttribute("message");
            try {
                CategoryDTO category = new CategoryDTO();
                category.setId(Integer.parseInt(parameter));
                category = categoryService.get(category);
                if (category != null) {
                    request.setAttribute("id", category.getId());
                    request.setAttribute("name", category.getName());
                    request.getRequestDispatcher("/view/categories/change-category.jsp").forward(request, response);
                } else {
                    request.getRequestDispatcher("/view/error/something-went-wrong.jsp").forward(request, response);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                response.sendRedirect(getServletContext().getContextPath() + "/categories");
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
        String SUCCESS_MESSAGE = "Category successfully updated!";
        String ALREADY_EXISTS_MESSAGE = "Category with this name already exists!";
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        HttpSession session = request.getSession();
        try {
            CategoryDTO category = new CategoryDTO();
            category.setId(id);
            category.setName(name);
            categoryService.update(category);
            session.setAttribute("message", SUCCESS_MESSAGE);
            response.sendRedirect(getServletContext().getContextPath() + "/categories");
        } catch (AlreadyExistsException e) {
            e.printStackTrace();
            session.setAttribute("message", ALREADY_EXISTS_MESSAGE);
            response.sendRedirect("change?id=" + id);
        } catch (DBException e) {
            e.printStackTrace();
            request.getRequestDispatcher("/view/error/db-error.jsp").forward(request, response);
        } catch (DTOConversionException e) {
            e.printStackTrace();
            request.getRequestDispatcher("/view/error/something-went-wrong.jsp").forward(request, response);
        }
    }

}
