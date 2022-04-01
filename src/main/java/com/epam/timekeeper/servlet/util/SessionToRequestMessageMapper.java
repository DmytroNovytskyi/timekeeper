package com.epam.timekeeper.servlet.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Contains only one method that maps messages from servlets to request for passing it to JSP pages.
 */
public class SessionToRequestMessageMapper {

    public static void map(HttpServletRequest request){
        HttpSession session = request.getSession();
        request.setAttribute("successMessage", session.getAttribute("successMessage"));
        request.setAttribute("warningMessage", session.getAttribute("warningMessage"));
        request.setAttribute("errorMessage", session.getAttribute("errorMessage"));
        session.removeAttribute("successMessage");
        session.removeAttribute("warningMessage");
        session.removeAttribute("errorMessage");
    }

}
