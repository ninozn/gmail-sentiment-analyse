package com.incentro.sa.util;

import com.incentro.sa.models.ChartValues;
import com.incentro.sa.models.EmailObject;
import com.incentro.sa.models.ValueLabel;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.incentro.sa.services.datastore.DatastoreService.getEmailObjectsByMail;

/**
 * Created by Tristan on 20-Feb-17 in gmail-sa
 */
public class DashboardDataUtil {
    
    public static List<EmailObject> getMailsToday(String primaryEmail) {
        List<EmailObject> emailObjectsByMail = getEmailObjectsByMail(primaryEmail);
        List<EmailObject> mailsToday = new ArrayList<>();
        
        if (emailObjectsByMail != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String today = sdf.format(new Date());
            
            for (EmailObject emailObject : emailObjectsByMail) {
                String actual = sdf.format(emailObject.getDateOfMail());
                if (actual.equals(today)) {
                    mailsToday.add(emailObject);
                }
            }
        }
        
        return mailsToday;
    }
    
    public static List<EmailObject> getMailsTotal(String primaryEmail) {
        List<EmailObject> emailObjectsByMail = getEmailObjectsByMail(primaryEmail);
        List<EmailObject> mailsTotal = new ArrayList<>();
        
        if (emailObjectsByMail != null) {
            for (EmailObject emailObject : emailObjectsByMail) {
                mailsTotal.add(emailObject);
            }
        }
        
        Collections.sort(mailsTotal, new Comparator<EmailObject>() {
            @Override
            public int compare(EmailObject o2, EmailObject o1) {
                return o1.getDateOfMail().compareTo(o2.getDateOfMail());
            }
        });
        
        return mailsTotal;
    }
    
    public static List<ValueLabel> getRecentMailValues(String primaryEmail) {
        List<ValueLabel> recentMailValues = null;
        try {
            recentMailValues = ChartUtil.makeLineChart(primaryEmail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recentMailValues;
    }
    
    public static ChartValues getChartValues(String primaryEmail) {
        ChartValues chartValues = null;
        try {
            chartValues = ChartUtil.makePieChart(primaryEmail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chartValues;
    }
    
    
}
