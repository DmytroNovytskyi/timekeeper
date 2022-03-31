package com.epam.timekeeper.filter;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter(filterName = "LangFilter")
public class LangFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        String lang = request.getParameter("lang");
        HttpServletResponse res = (HttpServletResponse) response;
        Cookie[] cookies = ((HttpServletRequest) request).getCookies();
        Cookie cookie;
        if (cookies != null) {
            List<Cookie> langCookies = Arrays.stream(cookies)
                    .filter(c -> c.getName().equals("lang")).toList();
            if (langCookies.isEmpty()) {
                cookie = new Cookie("lang", "en");
                res.addCookie(cookie);
            } else if (lang != null) {
                cookie = langCookies.get(0);
                if (lang.equals("English")) {
                    cookie.setValue("en");
                } else {
                    cookie.setValue("ua");
                }
                res.addCookie(cookie);
            }
        } else {
            cookie = new Cookie("lang", "en");
            res.addCookie(cookie);
        }
        chain.doFilter(request, response);
    }

}
