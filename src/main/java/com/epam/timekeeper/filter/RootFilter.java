package com.epam.timekeeper.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.timekeeper.servlet.util.constants.ServletUrn.*;

/**
 * If no servlet urn was specified, user will be redirected to the home page.
 */
public class RootFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.sendRedirect(request.getServletContext().getContextPath() + HOME);
    }

}
