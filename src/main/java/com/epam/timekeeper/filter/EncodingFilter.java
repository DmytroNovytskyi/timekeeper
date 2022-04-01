package com.epam.timekeeper.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * Sets encoding for request. Encoding value is read
 * from init param with the name "encoding".
 */
public class EncodingFilter implements Filter {

    private String encoding;

    @Override
    public void init(FilterConfig filterConfig) {
        encoding = filterConfig.getInitParameter("encoding");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        request.setCharacterEncoding(encoding);
        chain.doFilter(request, response);
    }

}
