package com.epam.timekeeper.servlet.categories;

import com.epam.timekeeper.dto.CategoryDTO;
import com.epam.timekeeper.dto.UserDTO;
import com.epam.timekeeper.exception.AlreadyExistsException;
import com.epam.timekeeper.exception.DBException;
import com.epam.timekeeper.service.CategoryService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.timekeeper.servlet.util.constants.Messages.Categories.*;
import static com.epam.timekeeper.servlet.util.constants.ServletUrn.*;

@WebServlet(name = "CategoryCreateServlet", value = CATEGORIES_CREATE)
public class CategoryCreateServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryCreateServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        HttpSession session = request.getSession();
        String logHeader = "session:" + session.getId() + ", username:" + ((UserDTO) session.getAttribute("user")).getUsername() + ". doPost -> ";
        if (name.matches(CATEGORY_NAME_REGEX)) {
            try {
                CategoryService categoryService = new CategoryService();
                categoryService.create(createDTO(name));
                session.setAttribute("successMessage", SUCCESS_CREATE_MESSAGE);
                LOGGER.info(logHeader + "Successfully complete.");
            } catch (AlreadyExistsException e) {
                LOGGER.error(logHeader + "AlreadyExistsException: " + e.getMessage());
                session.setAttribute("errorMessage", ALREADY_EXISTS_MESSAGE);
            } catch (DBException e) {
                LOGGER.error(logHeader + "DBException: " + e.getMessage());
                session.setAttribute("warningMessage", DB_EXCEPTION_MESSAGE);
            }
        } else {
            LOGGER.error(logHeader + "Passed data doesn't meet the requirements of category name: " + CATEGORY_NAME_REGEX);
            session.setAttribute("errorMessage", REQUIREMENTS_MESSAGE);
        }
        response.sendRedirect(getServletContext().getContextPath() + CATEGORIES);
    }

    private CategoryDTO createDTO(String name) {
        CategoryDTO category = new CategoryDTO();
        category.setName(name);
        return category;
    }

}
