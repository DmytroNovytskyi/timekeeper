package com.epam.timekeeper.filter;

import com.epam.timekeeper.dto.UserDTO;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static com.epam.timekeeper.servlet.util.constants.ServletUrn.*;

/**
 * Denies access for users that do not have WORKER role set for them.
 */
public class WorkerFilter implements Filter {

    private final static String ERROR_MESSAGE = "Access denied!";
    private final static Logger LOGGER = LoggerFactory.getLogger(WorkerFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession session = httpServletRequest.getSession();
        UserDTO user = (UserDTO) session.getAttribute("user");
        String logHeader = "session:" + session.getId() + ", username:" + user.getUsername() + ". doFilter -> ";
        if (!user.getRole().getName().equals("WORKER")) {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            session.setAttribute("errorMessage", ERROR_MESSAGE);
            httpServletResponse.sendRedirect(httpServletRequest.getServletContext().getContextPath() + HOME);
            LOGGER.info(logHeader + "Access denied.");
        } else {
            chain.doFilter(request, response);
        }
    }

}
