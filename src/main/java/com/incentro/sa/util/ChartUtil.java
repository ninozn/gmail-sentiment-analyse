package com.incentro.sa.util;

import com.incentro.sa.models.ChartValues;
import com.incentro.sa.models.EmailObject;
import com.incentro.sa.models.UserMailStatistics;
import com.incentro.sa.models.ValueLabel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;
import static com.incentro.sa.services.datastore.DatastoreService.getEmailObjectsByMail;

/**
 * Created by Roger on 15-12-2016 in gmail-sa
 */
public class ChartUtil {
    
    public static ChartValues makePieChart(String userEmail) throws Exception {
        ChartValues allChartValues = new ChartValues();
        UserMailStatistics userMailStatistics = ofy().load().type(UserMailStatistics.class).filter("userId", userEmail).first().now();
        
        if (userMailStatistics != null) {
            List<ValueLabel> valueLabelList = makeValueLabelList(userMailStatistics);
            
            List<ValueLabel> allPositiveValues = new ArrayList<>();
            allPositiveValues.add(new ValueLabel(userMailStatistics.getAllPositive(), "All Positive"));
            
            allChartValues.setBarChartPositiveValues(getIntegerTypeValues(allPositiveValues));
            allChartValues.setAllPositiveValues(allPositiveValues); //TODO what's this
            
            List<ValueLabel> allNegativeValues = new ArrayList<>();
            allNegativeValues.add(new ValueLabel(userMailStatistics.getAllNegative(), "All Negative"));
            
            allChartValues.setBarChartNegativeValues(getIntegerTypeValues(allNegativeValues));
            allChartValues.setAllNegativeValues(allNegativeValues); //TODO what's this
            
            allChartValues.setIntValues(getIntegerTypeValues(valueLabelList));
            allChartValues.setValueLabels(getValueLabelTypeValues(valueLabelList));
            allChartValues.setStringValues(getStringTypeValues(valueLabelList));
        }
        
        return allChartValues;
    }
    
    private static List<ValueLabel> makeValueLabelList(UserMailStatistics userMailStatistics) {
        List<ValueLabel> valueLabelList = new ArrayList<>();
        valueLabelList.add(new ValueLabel(userMailStatistics.getExtremely_negative(), "Extremely Negative"));
        valueLabelList.add(new ValueLabel(userMailStatistics.getNegative(), "Negative"));
        valueLabelList.add(new ValueLabel(userMailStatistics.getSlighty_negative(), "Slightly Negative"));
        valueLabelList.add(new ValueLabel(userMailStatistics.getNeutral(), "Neutral"));
        valueLabelList.add(new ValueLabel(userMailStatistics.getSlighty_positive(), "Slightly Positive"));
        valueLabelList.add(new ValueLabel(userMailStatistics.getPositive(), "Positive"));
        valueLabelList.add(new ValueLabel(userMailStatistics.getExtremely_postive(), "Extremely Positive"));
        return valueLabelList;
    }
    
    private static List<Integer> getIntegerTypeValues(List<ValueLabel> inputList) {
        List<Integer> integerList = new ArrayList<>();
        for (ValueLabel cv : inputList) {
            if (cv.getValue() > 0) {
                integerList.add(cv.getValue());
            }
        }
        return integerList;
    }
    
    private static List<String> getStringTypeValues(List<ValueLabel> inputList) {
        List<String> stringList = new ArrayList<>();
        for (ValueLabel cv : inputList) {
            if (cv.getValue() > 0) {
                stringList.add("\'" + cv.getValue() + "\'");
            }
        }
        return stringList;
    }
    
    private static List<ValueLabel> getValueLabelTypeValues(List<ValueLabel> inputList) {
        List<ValueLabel> valueLabelList = new ArrayList<>();
        for (ValueLabel cv : inputList) {
            if (cv.getValue() > 0) {
                valueLabelList.add(cv);
            }
        }
        return valueLabelList;
    }
    
    static List<ValueLabel> makeLineChart(String userEmail) throws Exception {
        List<EmailObject> eos = getEmailObjectsByMail(userEmail);
        
        if (eos != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            List<ValueLabel> recentMailValues = new ArrayList<>();
            
            String fourdays = sdf.format(new Date(System.currentTimeMillis() - 4L * 24 * 3600 * 1000));
            String threedays = sdf.format(new Date(System.currentTimeMillis() - 3L * 24 * 3600 * 1000));
            String twodays = sdf.format(new Date(System.currentTimeMillis() - 2L * 24 * 3600 * 1000));
            String onedays = sdf.format(new Date(System.currentTimeMillis() - 1L * 24 * 3600 * 1000));
            String zerodays = sdf.format(new Date(System.currentTimeMillis()));
            
            recentMailValues.add(new ValueLabel(0, fourdays));
            recentMailValues.add(new ValueLabel(0, threedays));
            recentMailValues.add(new ValueLabel(0, twodays));
            recentMailValues.add(new ValueLabel(0, onedays));
            recentMailValues.add(new ValueLabel(0, zerodays));
            
            for (EmailObject eo : eos) {
                if (sdf.format(eo.getDateOfMail()).equals(fourdays)) {
                    recentMailValues.get(0).addToValue(1);
                }
                else if (sdf.format(eo.getDateOfMail()).equals(threedays)) {
                    recentMailValues.get(1).addToValue(1);
                } else if (sdf.format(eo.getDateOfMail()).equals(twodays)) {
                    recentMailValues.get(2).addToValue(1);
                } else if (sdf.format(eo.getDateOfMail()).equals(onedays)) {
                    recentMailValues.get(3).addToValue(1);
                } else if (sdf.format(eo.getDateOfMail()).equals(zerodays)) {
                    recentMailValues.get(4).addToValue(1);
                }
            }
            return recentMailValues;
        }
        return null;
    }
}
