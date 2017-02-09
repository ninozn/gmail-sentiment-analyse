package com.incentro.sa.Servlets;

import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.services.oauth2.model.Tokeninfo;
import com.incentro.sa.GoogleOAuth2Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class Oauth2CallBackServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(Oauth2CallBackServlet.class.getName());


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        GoogleOAuth2Util googleOauth2util = new GoogleOAuth2Util();
        // Parse the state param to a key value hashmap

        // Getting the "error" URL parameter
        String[] error = httpServletRequest.getParameterValues("error");

        // Checking if there was an error such as the user denied access
        if (error != null && error.length > 0) {
//            httpServletResponse.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, "There was an error: \""+error[0]+"\".");
            httpServletRequest.setAttribute("errormessage","Something went wrong, please try again");
            httpServletRequest.getRequestDispatcher("home.jsp").forward(httpServletRequest, httpServletResponse);
            return;
        }



        String state = httpServletRequest.getParameter("state");
        Map<String, String> stateParams = stringToMap(state, "&", "=");

        String authorisationCode = httpServletRequest.getParameter("code");
        String intendedUrl = java.net.URLDecoder.decode(stateParams.get("intended"), "UTF-8");



        if (authorisationCode == null) {
            httpServletResponse.sendRedirect("http://sentiment-analyse.appspot.com//home.jsp");
            return;
        }

        if (intendedUrl == null) {
            throw new ServletException("Looks like you didn't give us a valid intended url.");
        }

        if (authorisationCode != null) {

            Tokeninfo tokenInfo = null;

            try {
                tokenInfo = googleOauth2util.getTokenInfo(httpServletRequest, authorisationCode);
            } catch (TokenResponseException e) {
                httpServletRequest.setAttribute("errormessage","We couldn't get a valid token.");
                httpServletRequest.getRequestDispatcher("/login").forward(httpServletRequest, httpServletResponse);
                return;
            }
            if(tokenInfo == null){
                httpServletRequest.setAttribute("errormessage","Please authenticate with the same email.");
                httpServletRequest.getRequestDispatcher("home.jsp").forward(httpServletRequest, httpServletResponse);
                return;
            }

            if (tokenInfo.getExpiresIn() == null || tokenInfo.getExpiresIn() < 100) {
                throw new ServletException("Looks like we couldn't authenticate you!");
            }

            // Google ID
            String loggedInUserId = tokenInfo.getUserId();
            httpServletRequest.getSession().setAttribute("loggedInUserId", loggedInUserId);

            // Google Email (for dev, because dev doesn't send out proper ids)
            String loggedInUserEmail = tokenInfo.getEmail();
            httpServletRequest.getSession().setAttribute("loggedInUserEmail", loggedInUserEmail);

            // The user is authenticated. Let's redirect him/her to the intranet
            //httpServletResponse.sendRedirect(intendedUrl);
            httpServletRequest.setAttribute("message","Successfully subscribed");
            httpServletRequest.getRequestDispatcher("home.jsp").forward(httpServletRequest, httpServletResponse);
            return;
        }

        throw new ServletException("Something went wrong!");

    }

    private Map<String, String> stringToMap(String source, String entriesSeparator, String keyValueSeparator) {
        Map<String, String> map = new HashMap<String, String>();
        String[] entries = source.split(entriesSeparator);
        for (String entry : entries) {

            if (!entry.equals("") && entry.contains(keyValueSeparator)) {
                String[] keyValue = entry.split(keyValueSeparator);
                map.put(keyValue[0], keyValue[1]);
            }
        }
        return map;
    }
}
