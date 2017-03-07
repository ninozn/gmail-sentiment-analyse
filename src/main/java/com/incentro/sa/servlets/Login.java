package com.incentro.sa.servlets;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.incentro.sa.models.ChartValues;
import com.incentro.sa.models.EmailObject;
import com.incentro.sa.models.ValueLabel;
import com.incentro.sa.util.DashboardDataUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class Login extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();
        String primaryEmail = currentUser.getEmail();
    
        List<EmailObject> mailsToday = DashboardDataUtil.getMailsToday(primaryEmail);
        List<EmailObject> mailsTotal = DashboardDataUtil.getMailsTotal(primaryEmail);
    
        // try receiving the chart info
        ChartValues chartValues = DashboardDataUtil.getChartValues(primaryEmail);
        List<ValueLabel> recentMailValues = DashboardDataUtil.getRecentMailValues(primaryEmail);
    
        // push all request objects.
        if (mailsTotal.size()!= 0 && mailsToday.size() >= 6) {
            request.setAttribute("emailContentToday", mailsTotal.subList(0,6));
        } else if (mailsTotal.size() != 0 && mailsToday.size() < 6) {
            request.setAttribute("emailContentToday", mailsTotal.subList(0,mailsToday.size()));
        }
        if (chartValues != null) {
            request.setAttribute("chartValues", chartValues);
        }
        request.setAttribute("recentMailValues", recentMailValues);
        request.setAttribute("emailTotal", mailsTotal);
        request.setAttribute("mailsToday",mailsToday.size());
        request.getRequestDispatcher("home.jsp").forward(request, response);
    }
}
