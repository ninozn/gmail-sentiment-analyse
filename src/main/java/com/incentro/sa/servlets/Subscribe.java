package com.incentro.sa.servlets;

import com.incentro.sa.services.GoogleOAuth2Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Subscribe extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        //TODO What is happening here?
        GoogleOAuth2Util googleoauth2util = new GoogleOAuth2Util();
        String subscribeUrl = googleoauth2util.getSubscribeUrl(httpServletRequest);
        httpServletResponse.sendRedirect(subscribeUrl);
    }
}
