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
        Cookie cookie;
        boolean hasLang = false;
        if (lang == null) {
            Cookie[] cs = ((HttpServletRequest) request).getCookies();
            if (cs != null) {
                List<Cookie> cookies = Arrays.stream(cs).filter(c -> c.getName().equals("lang")).toList();
                if (cookies.isEmpty()) {
                    cookie = new Cookie("lang", "en");
                } else {
                    cookie = cookies.get(0);
                }
            } else {
                cookie = new Cookie("lang", "en");
            }
        } else {
            if (lang.equals("English")) {
                cookie = new Cookie("lang", "en");
            } else {
                cookie = new Cookie("lang", "ua");
            }
        }
        res.addCookie(cookie);
        chain.doFilter(request, response);
    }

}
