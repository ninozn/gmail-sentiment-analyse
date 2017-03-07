package com.incentro.sa.servlets;

import com.incentro.sa.models.GoogleUser;
import com.incentro.sa.services.datastore.DatastoreService;
import com.incentro.sa.util.MailLabelUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetMailServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Build a new authorized API client service.
        List<GoogleUser> allSubscribedUsers = DatastoreService.getAllSubscribedUsers();
        if (allSubscribedUsers != null) {
            for (GoogleUser googleUser : allSubscribedUsers) {
                MailLabelUtil.checkLabelsAndAnalyseMail(googleUser);
            }
        }
        request.setAttribute("message", "Emails have been analysed.");
        request.getRequestDispatcher("home.jsp").forward(request, response);
    }
}
