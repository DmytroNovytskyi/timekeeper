package com.epam.timekeeper.servlet.activities;

import com.epam.timekeeper.dto.ActivityDTO;
import com.epam.timekeeper.dto.CategoryDTO;
import com.epam.timekeeper.exception.AlreadyExistsException;
import com.epam.timekeeper.exception.DBException;
import com.epam.timekeeper.exception.DTOConversionException;
import com.epam.timekeeper.service.ActivityService;
import com.epam.timekeeper.service.CategoryService;
import com.epam.timekeeper.service.mapper.ActivityDTOMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "NewActivityServlet", value = "/activities/new")
public class NewActivityServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CategoryService categoryService = new CategoryService();
        HttpSession session = request.getSession();
        request.setAttribute("message", session.getAttribute("message"));
        session.removeAttribute("message");
        try {
            List<CategoryDTO> categories = categoryService.getAll();
            if (categories != null) {
                request.setAttribute("list", categories);
                request.getRequestDispatcher("/view/activities/new-activity.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/view/error/no-categories-found.jsp").forward(request, response);
            }
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
        String SUCCESS_MESSAGE = "Activity successfully created!";
        String ALREADY_EXISTS_MESSAGE = "Activity with this name already exists!";
        HttpSession session = request.getSession();
        try {
            ActivityService activityService = new ActivityService();
            int id = Integer.parseInt(request.getParameter("category"));
            String name = request.getParameter("activity");
            activityService.create(createDTO(id, name));
            session.setAttribute("message", SUCCESS_MESSAGE);
            response.sendRedirect(getServletContext().getContextPath() + "/activities");
        } catch (AlreadyExistsException e) {
            e.printStackTrace();
            session.setAttribute("message", ALREADY_EXISTS_MESSAGE);
            response.sendRedirect("new");
        } catch (DBException e) {
            e.printStackTrace();
            request.getRequestDispatcher("/view/error/db-error.jsp").forward(request, response);
        }
    }

    private ActivityDTO createDTO(int id, String name){
        ActivityDTO activityDTO = new ActivityDTO();
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(id);
        activityDTO.setCategory(categoryDTO);
        activityDTO.setName(name);
        return activityDTO;
    }
}
