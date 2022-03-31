package com.epam.timekeeper.servlet.categories;

import com.epam.timekeeper.dto.FullCategoryDTO;
import com.epam.timekeeper.dto.UserDTO;
import com.epam.timekeeper.exception.AlreadyExistsException;
import com.epam.timekeeper.exception.DBException;
import com.epam.timekeeper.exception.ObjectNotFoundException;
import com.epam.timekeeper.service.CategoryService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.timekeeper.servlet.util.constants.Messages.Activities.DB_EXCEPTION_MESSAGE;
import static com.epam.timekeeper.servlet.util.constants.Messages.Activities.DB_EXCEPTION_MESSAGE_UA;
import static com.epam.timekeeper.servlet.util.constants.Messages.Categories.*;
import static com.epam.timekeeper.servlet.util.constants.ServletUrn.*;

@WebServlet(name = "CategoryUpdateServlet", value = CATEGORY_UPDATE)
public class CategoryUpdateServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryUpdateServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String lang = Arrays.stream(request.getCookies()).filter(c -> c.getName().equals("lang")).toList().get(0).getValue();
        int id = Integer.parseInt(request.getParameter("id"));
        String enName = request.getParameter("enName");
        String uaName = request.getParameter("uaName");
        String logHeader = "session:" + session.getId() + ", username:" + ((UserDTO) session.getAttribute("user")).getUsername() + ". doPost -> ";
        if (enName.matches(CATEGORY_EN_NAME_REGEX) && uaName.matches(CATEGORY_UA_NAME_REGEX)) {
            try {
                CategoryService categoryService = new CategoryService();
                categoryService.update(createFullDTO(id, enName, uaName));
                LOGGER.info(logHeader + "Successfully complete.");
                if(lang.equals("en")) {
                    session.setAttribute("successMessage", SUCCESS_UPDATE_MESSAGE);
                } else {
                    session.setAttribute("successMessage", SUCCESS_UPDATE_MESSAGE_UA);
                }
            } catch (AlreadyExistsException e) {
                LOGGER.error(logHeader + "AlreadyExistsException: " + e.getMessage());
                if(lang.equals("en")) {
                    session.setAttribute("errorMessage", ALREADY_EXISTS_MESSAGE);
                } else {
                    session.setAttribute("errorMessage", ALREADY_EXISTS_MESSAGE_UA);
                }
            } catch (DBException e) {
                LOGGER.error(logHeader + "DBException: " + e.getMessage());
                if(lang.equals("en")) {
                    session.setAttribute("warningMessage", DB_EXCEPTION_MESSAGE);
                } else {
                    session.setAttribute("warningMessage", DB_EXCEPTION_MESSAGE_UA);
                }
            } catch (ObjectNotFoundException e) {
                LOGGER.error(logHeader + "ObjectNotFoundException: " + e.getMessage());
                if(lang.equals("en")) {
                    session.setAttribute("warningMessage", NOT_FOUND_MESSAGE);
                } else {
                    session.setAttribute("warningMessage", NOT_FOUND_MESSAGE_UA);
                }
            }
        } else {
            LOGGER.error(logHeader + "Passed data doesn't meet the requirements of category name for en: "
                    + CATEGORY_EN_NAME_REGEX + "or ua: " + CATEGORY_UA_NAME_REGEX);
            if(lang.equals("en")) {
                session.setAttribute("errorMessage", REQUIREMENTS_MESSAGE);
            } else {
                session.setAttribute("errorMessage", REQUIREMENTS_MESSAGE_UA);
            }
        }
        response.sendRedirect(getServletContext().getContextPath() + CATEGORIES);
    }

    private FullCategoryDTO createFullDTO(int id, String enName, String uaName) {
        FullCategoryDTO category = new FullCategoryDTO();
        category.setId(id);
        HashMap<String, String> map = new HashMap<>();
        map.put("en", enName);
        map.put("ua", uaName);
        category.setLangName(map);
        return category;
    }

}
