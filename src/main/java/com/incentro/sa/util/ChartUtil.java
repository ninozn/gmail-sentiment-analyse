package com.incentro.sa.util;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.incentro.sa.models.ChartValue;
import com.incentro.sa.models.EmailObject;
import com.incentro.sa.models.PieChartValues;
import com.incentro.sa.models.UsersInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by Roger on 15-12-2016.
 */
public class ChartUtil {

    public static PieChartValues makePieChart(String userEmail) throws Exception {
        PieChartValues pieChartValues = new PieChartValues();

        UsersInfo ui = ofy().load().type(UsersInfo.class).filter("userId",userEmail).first().now();
        if(ui!=null) {
            List<ChartValue> values = new ArrayList<>();
            values.add(new ChartValue(ui.getExtremely_negative(), "Extremely Negative"));
            values.add(new ChartValue(ui.getNegative(), "Negative"));
            values.add(new ChartValue(ui.getSlighty_negative(), "Slightly Negative"));
            values.add(new ChartValue(ui.getNeutral(), "Neutral"));
            values.add(new ChartValue(ui.getSlighty_positive(), "Slightly Positive"));
            values.add(new ChartValue(ui.getPositive(), "Positive"));
            values.add(new ChartValue(ui.getExtremely_postive(), "Extremely Positive"));

            List<ChartValue> AllPvalues = new ArrayList<>();
            List<ChartValue> AllNvalues = new ArrayList<>();
            AllPvalues.add(new ChartValue(ui.getAllPositive(), "All Positive"));
            AllNvalues.add(new ChartValue(ui.getAllNegative(), "All Negative"));
            pieChartValues.setAllNegativeValues(AllNvalues); //TODO what's this
            pieChartValues.setAllPositiveValues(AllPvalues);


            List<Integer> barNValues = new ArrayList<>();
            for (ChartValue cv : AllNvalues) {
                if (cv.getValue() > 0) {
                    barNValues.add(cv.getValue());
                }
            }
            pieChartValues.setBarNValues(barNValues);

            List<Integer> barPValues = new ArrayList<>();
            for (ChartValue cv : AllPvalues) {
                if (cv.getValue() > 0) {
                    barPValues.add(cv.getValue());
                }
            }
            pieChartValues.setBarPValues(barPValues);

            List<ChartValue> chartValues = new ArrayList<>();
            for (ChartValue cv : values) {
                if (cv.getValue() > 0) {
                    chartValues.add(cv);
                }
            }
            pieChartValues.setChartValues(chartValues);

            List<Integer> intValues = new ArrayList<>();
            for (ChartValue cv : values) {
                if (cv.getValue() > 0) {
                    intValues.add(cv.getValue());
                }
            }
            pieChartValues.setIntValues(intValues);

            List<String> stringValues = new ArrayList<>();
            for (ChartValue cv : values) {
                if (cv.getValue() > 0) {
                    stringValues.add("\'" + cv.getValue() + "\'");
                }
            }
            pieChartValues.setStringValues(stringValues);
        }

        return pieChartValues;
    }
    public static List<ChartValue> makeLineChart(String userEmail) throws Exception {
        UserService userService = UserServiceFactory.getUserService();
        User serviceuser = userService.getCurrentUser();
        List<EmailObject> eos = ofy().load().type(EmailObject.class).filter("primaryEmail",serviceuser.getEmail()).list();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        List<ChartValue> recentMailValues = new ArrayList<>();
        if (eos!= null){
            String fourdays = sdf.format(new Date(System.currentTimeMillis() - 4L * 24 * 3600 * 1000));
            String threedays = sdf.format(new Date(System.currentTimeMillis() - 3L * 24 * 3600 * 1000));
            String twodays = sdf.format(new Date(System.currentTimeMillis() - 2L * 24 * 3600 * 1000));
            String onedays = sdf.format(new Date(System.currentTimeMillis() - 1L * 24 * 3600 * 1000));
            String zerodays = sdf.format(new Date(System.currentTimeMillis()));



            int fourdaysInt = 0;
            int threedaysInt = 0;
            int twodaysInt = 0;
            int onedaysInt = 0;
            int zerodaysInt = 0;

            for (EmailObject eo : eos){
                if (sdf.format(eo.getDateOfMail()).equals(fourdays)) {
                    fourdaysInt ++;
                }
                else if (sdf.format(eo.getDateOfMail()).equals(threedays)) {
                    threedaysInt ++;
                }
                if (sdf.format(eo.getDateOfMail()).equals(twodays)) {
                    twodaysInt ++;
                }
                if (sdf.format(eo.getDateOfMail()).equals(onedays)) {
                    onedaysInt ++;
                }
                if (sdf.format(eo.getDateOfMail()).equals(zerodays)) {
                    zerodaysInt ++;
                }
            }

            recentMailValues.add(new ChartValue(fourdaysInt, fourdays));
            recentMailValues.add(new ChartValue(threedaysInt, threedays));
            recentMailValues.add(new ChartValue(twodaysInt, twodays));
            recentMailValues.add(new ChartValue(onedaysInt, onedays));
            recentMailValues.add(new ChartValue(zerodaysInt, zerodays));

            return recentMailValues;
        }
        return null;
    }
}
