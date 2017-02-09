package com.incentro.sa.filters;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AccessMailFilter implements Filter {
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("init");
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        String cron = request.getHeader("X-Appengine-Cron");
        if (cron != null){
            chain.doFilter(req, resp);
            return;
        }

        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        if (user.getEmail().equals("admin")) {
            //if user is logged in, complete request
            chain.doFilter(req, resp);
        } else {
            //not logged in, go to login page
            req.setAttribute("errormessage", "You do not have the rights to call this servlet");
            req.getRequestDispatcher("home.jsp").forward(req, resp);
        }

    }
}
