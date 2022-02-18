package com.epam.timekeeper.servlet.activities;

import com.epam.timekeeper.dto.ActivityDTO;
import com.epam.timekeeper.dto.CategoryDTO;
import com.epam.timekeeper.exception.AlreadyExistsException;
import com.epam.timekeeper.exception.DBException;
import com.epam.timekeeper.exception.DTOConversionException;
import com.epam.timekeeper.service.ActivityService;
import com.epam.timekeeper.service.CategoryService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ActivityChangeServlet", value = "/activities/change")
public class ActivityChangeServlet extends HttpServlet {

    private final ActivityService activityService = new ActivityService();
    private final CategoryService categoryService = new CategoryService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String parameter = request.getParameter("id");
        if (parameter == null) {
            response.sendRedirect(getServletContext().getContextPath() + "/activities");
        } else {
            HttpSession session = request.getSession();
            request.setAttribute("message", session.getAttribute("message"));
            session.removeAttribute("message");
            try {
                ActivityDTO activity = new ActivityDTO();
                activity.setId(Integer.parseInt(parameter));
                activity = activityService.get(activity);
                List<CategoryDTO> categories = categoryService.getAll();
                if (categories != null && activity != null) {
                    request.setAttribute("activity", activity);
                    request.setAttribute("categories", categories);
                    request.getRequestDispatcher("/view/activities/change-activity.jsp").forward(request, response);
                } else {
                    request.getRequestDispatcher("/view/error/something-went-wrong.jsp").forward(request, response);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                response.sendRedirect(getServletContext().getContextPath() + "/activities");
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
        String SUCCESS_MESSAGE = "Activity successfully updated!";
        String ALREADY_EXISTS_MESSAGE = "Activity with this name already exists!";
        int activityId = Integer.parseInt(request.getParameter("activityId"));
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        String activityName = request.getParameter("activityName");
        HttpSession session = request.getSession();
        try {
            activityService.update(createDTO(activityId, categoryId, activityName));
            session.setAttribute("message", SUCCESS_MESSAGE);
            response.sendRedirect(getServletContext().getContextPath() + "/activities");
        } catch (AlreadyExistsException e) {
            e.printStackTrace();
            session.setAttribute("message", ALREADY_EXISTS_MESSAGE);
            response.sendRedirect("change?id=" + activityId);
        } catch (DBException e) {
            e.printStackTrace();
            request.getRequestDispatcher("/view/error/db-error.jsp").forward(request, response);
        } catch (DTOConversionException e) {
            e.printStackTrace();
            request.getRequestDispatcher("/view/error/something-went-wrong.jsp").forward(request, response);
        }
    }

    private ActivityDTO createDTO(int activityId, int categoryId, String activityName){
        ActivityDTO activityDTO = new ActivityDTO();
        CategoryDTO categoryDTO = new CategoryDTO();
        activityDTO.setId(activityId);
        categoryDTO.setId(categoryId);
        activityDTO.setCategory(categoryDTO);
        activityDTO.setName(activityName);
        return activityDTO;
    }
}
