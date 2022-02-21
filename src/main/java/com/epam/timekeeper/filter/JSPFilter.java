package com.epam.timekeeper.filter;

import javax.servlet.*;
import java.io.IOException;

import static com.epam.timekeeper.servlet.util.constants.JspUrn.*;

public class JSPFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
       request.getRequestDispatcher(NOT_FOUND_JSP).forward(request,response);
    }
}
