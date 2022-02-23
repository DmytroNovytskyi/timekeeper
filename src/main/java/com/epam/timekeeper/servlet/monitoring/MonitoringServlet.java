package com.epam.timekeeper.servlet.monitoring;

import com.epam.timekeeper.dto.UserDTO;
import com.epam.timekeeper.dto.UserHasActivityDTO;
import com.epam.timekeeper.exception.DBException;
import com.epam.timekeeper.exception.DTOConversionException;
import com.epam.timekeeper.service.UserHasActivityService;
import com.epam.timekeeper.servlet.util.SessionToRequestMessageMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.timekeeper.servlet.util.constants.JspUrn.*;
import static com.epam.timekeeper.servlet.util.constants.Messages.Monitoring.*;
import static com.epam.timekeeper.servlet.util.constants.ServletUrn.*;

@WebServlet(name = "MonitoringServlet", value = MONITORING)
public class MonitoringServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(MonitoringServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionToRequestMessageMapper.map(request);
        HttpSession session = request.getSession();
        String logHeader = "session:" + session.getId() + ", username:"
                + ((UserDTO) session.getAttribute("user")).getUsername() + ". doGet -> ";
        try {
            UserHasActivityService userHasActivityService = new UserHasActivityService();
            List<UserHasActivityDTO> list = userHasActivityService.getAll();
            request.setAttribute("list", list);
            request.setAttribute("statuses", userHasActivityService.getUniqueStatuses(list));
            request.setAttribute("categories", userHasActivityService.getUniqueCategories(list));
            request.setAttribute("activities", userHasActivityService.getUniqueActivities(list));
            request.setAttribute("users", userHasActivityService.getUniqueUsers(list));
            request.getRequestDispatcher(MONITORING_JSP).forward(request, response);
            LOGGER.info(logHeader + "Successfully complete.");
        } catch (DBException e) {
            LOGGER.error(logHeader + "DBException: " + e.getMessage());
            session.setAttribute("warningMessage", DB_EXCEPTION_MESSAGE);
            response.sendRedirect(getServletContext().getContextPath() + HOME);
        } catch (DTOConversionException e) {
            LOGGER.error(logHeader + "DTOConversionException: " + e.getMessage());
            session.setAttribute("errorMessage", DTO_CONVERSION_MESSAGE);
            response.sendRedirect(getServletContext().getContextPath() + HOME);
        }

    }

}
