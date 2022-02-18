package com.epam.timekeeper.filter;

import com.epam.timekeeper.dto.UserDTO;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminFilter implements Filter {

    private final static String ERROR_MESSAGE = "Access denied!";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        UserDTO user = (UserDTO) httpServletRequest.getSession().getAttribute("user");
        if (!user.getRole().getName().equals("ADMIN")) {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            httpServletRequest.getSession().setAttribute("errorMessage", ERROR_MESSAGE);
            httpServletResponse.sendRedirect(httpServletRequest.getServletContext().getContextPath() + "/home");
        } else {
            chain.doFilter(request, response);
        }
    }

}
