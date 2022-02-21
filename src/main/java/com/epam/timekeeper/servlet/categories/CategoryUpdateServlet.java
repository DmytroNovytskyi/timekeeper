package com.epam.timekeeper.servlet.categories;

import com.epam.timekeeper.dto.CategoryDTO;
import com.epam.timekeeper.dto.UserDTO;
import com.epam.timekeeper.exception.AlreadyExistsException;
import com.epam.timekeeper.exception.DBException;
import com.epam.timekeeper.exception.ObjectNotFoundException;
import com.epam.timekeeper.service.CategoryService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "CategoryUpdateServlet", value = "/categories/update")
public class CategoryUpdateServlet extends HttpServlet {

    private final static String SUCCESS_MESSAGE = "Category successfully updated!";
    private final static String ALREADY_EXISTS_MESSAGE = "Category with this name already exists!";
    private final static String WARNING_MESSAGE = "Database error occurred while trying to access categories. Please try again later.";
    private final static String NOT_FOUND_MESSAGE = "Category was not found.";
    private final static String REQUIREMENTS_MESSAGE = "Category doesn't match requirements. Please try again.";

    private final static String CATEGORY_NAME_REGEX = "^[\\sa-zA-Z0-9/.-]{8,45}$";

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryUpdateServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        HttpSession session = request.getSession();
        String logHeader = "session:" + session.getId() + ", username:" + ((UserDTO) session.getAttribute("user")).getUsername() + ". doPost -> ";
        if (name.matches(CATEGORY_NAME_REGEX)) {
            try {
                CategoryService categoryService = new CategoryService();
                categoryService.update(createDTO(id, name));
                session.setAttribute("successMessage", SUCCESS_MESSAGE);
                LOGGER.info(logHeader + "Successfully complete.");
            } catch (AlreadyExistsException e) {
                LOGGER.error(logHeader + "AlreadyExistsException: " + e.getMessage());
                session.setAttribute("errorMessage", ALREADY_EXISTS_MESSAGE);
            } catch (DBException e) {
                LOGGER.error(logHeader + "DBException: " + e.getMessage());
                session.setAttribute("warningMessage", WARNING_MESSAGE);
            } catch (ObjectNotFoundException e) {
                LOGGER.error(logHeader + "ObjectNotFoundException: " + e.getMessage());
                session.setAttribute("warningMessage", NOT_FOUND_MESSAGE);
            }
        } else {
            LOGGER.error(logHeader + "Passed data doesn't meet the requirements of category name: " + CATEGORY_NAME_REGEX);
            session.setAttribute("errorMessage", REQUIREMENTS_MESSAGE);
        }
        response.sendRedirect(getServletContext().getContextPath() + "/categories");
    }

    private CategoryDTO createDTO(int id, String name) {
        CategoryDTO category = new CategoryDTO();
        category.setId(id);
        category.setName(name);
        return category;
    }

}
