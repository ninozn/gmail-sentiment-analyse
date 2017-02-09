package com.incentro.sa.filters;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

public class AuthenticationFilter implements Filter {

    private String[] excludedUrls;
    private final Logger LOGGER = Logger.getLogger(AuthenticationFilter.class.getName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        excludedUrls = filterConfig.getInitParameter("excluded").split(","); //urls come from config in web.xml
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String path = ((HttpServletRequest) request).getRequestURI();
        HttpServletRequest hsr = (HttpServletRequest) request;
        String cron = hsr.getHeader("X-Appengine-Cron");
        if (cron != null){
            chain.doFilter(request, response);
            return;
        }
        for (String excludedUrl : excludedUrls) {
            if (path.startsWith(excludedUrl)) {
                chain.doFilter(request, response);
                return;
            }
        }

        // The root is not password protected
        if (path.equals("/") || path.equals("/index.jsp")) { //|| path.equals("/index.jsp") zelf ge-add
            chain.doFilter(request, response);
            return;
        }

        UserService userService = UserServiceFactory.getUserService();
        User currentUserFromService = userService.getCurrentUser();

        if (currentUserFromService == null && cron == null) {
            LOGGER.info("The user service did not find a logged in user. Redirecting the user to the Google login.");
            ((HttpServletResponse) response).sendRedirect(userService.createLoginURL(((HttpServletRequest) request).getRequestURI()));
            return;
        }
        request.setAttribute("logout_url", userService.createLogoutURL("/"));
        request.setAttribute("currentUser", currentUserFromService);
        if(1==1){
            chain.doFilter(request,response);
            return;
        }
        LOGGER.info("The user service did not find a logged in/authorized user. Redirecting the user to the Google login.");
        request.getRequestDispatcher("/pages/exception/noAccess.jsp").forward(request, response);
    }

    @Override
    public void destroy() {

    }
}