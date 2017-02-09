package com.incentro.sa.Servlets;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;
import com.google.api.services.gmail.model.ListLabelsResponse;
import com.incentro.sa.GmailAPIService;
import com.incentro.sa.models.GoogleUser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

@SuppressWarnings("DefaultFileTemplate")
public class getMailServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Build a new authorized API client service.
        List<GoogleUser> allGoogleUsers = ofy().load().type(GoogleUser.class).list();
        for(GoogleUser gu : allGoogleUsers)
        {
            Gmail service = GmailAPIService.getGmailService(gu);
            List<String> labellijst = Arrays.asList("Language Unknown", "Slightly Negative", "Negative","Extremely Negative","Slightly Positive","Positive","Extremely Positive","Neutral");
            // Print the labels in the user's account.
            String user = gu.getPrimaryEmail();

            ListLabelsResponse listResponse =
                    service.users().labels().list(user).execute();

            List<Label> labels = listResponse.getLabels();

            List<String> labelids = new ArrayList<String>();
            for (Label label : labels) {
                if(label.getName().equals("INBOX")){
                    labelids.add(label.getId());
                }
            }
            for(String s : labellijst)
            {
                try{
                    service.users().labels().get(user, GmailAPIService.getLabelIdByName(service,user,s)).execute();
                }
                catch (Exception e){
                    Label label = new Label().setName(s).setLabelListVisibility("labelShow").setMessageListVisibility("show");
                    service.users().labels().create(user, label).execute();
                }
            }
            try {
                GmailAPIService.listMessagesWithLabels(service,user,labelids,gu);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        request.setAttribute("message","Emails have been analysed.");
        request.getRequestDispatcher("home.jsp").forward(request, response);
    }
}
