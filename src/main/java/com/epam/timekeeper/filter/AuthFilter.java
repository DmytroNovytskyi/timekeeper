package com.epam.timekeeper.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.timekeeper.servlet.util.constants.ServletUrn.*;

public class AuthFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession session = httpServletRequest.getSession();
        String logHeader = "session:" + session.getId() + ". doFilter -> ";
        if (session.getAttribute("user") == null) {
            ((HttpServletResponse) response).sendRedirect(httpServletRequest.getServletContext().getContextPath() + AUTH);
            LOGGER.info(logHeader + "Not authorized.");
        } else {
            chain.doFilter(request, response);
        }
    }

}
