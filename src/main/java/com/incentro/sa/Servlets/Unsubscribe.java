package com.incentro.sa.Servlets;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.incentro.sa.models.GoogleUser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class Unsubscribe extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        if(ofy().load().type(GoogleUser.class).filter("primaryEmail",user.getEmail()).first().now() != null)
        {
            GoogleUser gu = ofy().load().type(GoogleUser.class).filter("primaryEmail",user.getEmail()).first().now();
            ofy().delete().entity(gu).now();
            request.setAttribute("message","Succesfully unsubscribed");
        }
        else{
            request.setAttribute("errormessage","Could not find your account in the database");
        }

        request.getRequestDispatcher("home.jsp").forward(request, response);
    }
}
