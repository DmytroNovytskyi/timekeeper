package com.epam.timekeeper.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        if (httpServletRequest.getSession().getAttribute("user") == null) {
            ((HttpServletResponse) response).sendRedirect(httpServletRequest.getServletContext().getContextPath() + "/auth");
        } else {
            chain.doFilter(request, response);
        }
    }

}
