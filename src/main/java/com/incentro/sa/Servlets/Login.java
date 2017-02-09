package com.incentro.sa.Servlets;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.incentro.sa.models.ChartValue;
import com.incentro.sa.models.EmailObject;
import com.incentro.sa.models.PieChartValues;
import com.incentro.sa.util.ChartUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class Login extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService userService = UserServiceFactory.getUserService();
        User serviceuser = userService.getCurrentUser();
        List<EmailObject> eos = ofy().load().type(EmailObject.class).filter("primaryEmail",serviceuser.getEmail()).list();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String today = sdf.format(new Date());
        List<EmailObject> mailsToday = new ArrayList<>();
        List<EmailObject> mailsTotal = new ArrayList<>();

        for (EmailObject e : eos){
            mailsTotal.add(e);
            String actual = sdf.format(e.getDateOfMail());
            if (actual.equals(today)) {
                mailsToday.add(e);
            }
        }

             // try receiving the chart info
        PieChartValues pieChartValues = null;
        try {
            pieChartValues = ChartUtil.makePieChart(serviceuser.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<ChartValue> recentMailValues = null;
        try {
            recentMailValues = ChartUtil.makeLineChart(serviceuser.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Collections.sort(mailsTotal, new Comparator<EmailObject>() {
        @Override
        public int compare(EmailObject o2, EmailObject o1) {
            return o1.getDateOfMail().compareTo(o2.getDateOfMail());
        }
        });

            // push all request objects.
//        if (mailsToday.size()!= 0) {request.setAttribute("emailContentToday", mailsToday.subList(0,6));}
        if (mailsTotal.size()!= 0 && mailsToday.size() >= 6) {
            request.setAttribute("emailContentToday", mailsTotal.subList(0,6));
        }
        if (mailsTotal.size()!= 0 && mailsToday.size() <6) {
            request.setAttribute("emailContentToday", mailsTotal.subList(0,mailsToday.size()));
        }
        if (pieChartValues != null) {request.setAttribute("pieChartValues", pieChartValues);}
        request.setAttribute("recentMailValues", recentMailValues);
        request.setAttribute("emailTotal", mailsTotal);
        request.setAttribute("mailsToday",mailsToday.size());
        request.getRequestDispatcher("home.jsp").forward(request, response);
    }
}
