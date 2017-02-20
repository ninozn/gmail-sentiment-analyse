package com.incentro.sa.models;

import java.util.List;

/**
 * Created by Roger on 15-12-2016.
 */
public class ChartValues {
    
    private List<Integer> barChartNegativeValues;
    private List<Integer> barChartPositiveValues;
    private List<ValueLabel> valueLabels;
    private List<Integer> intValues;
    private List<String> stringValues;
    private List<ValueLabel> allPositiveValues;
    private List<ValueLabel> allNegativeValues;
    
    public ChartValues() {
    }
    
    public ChartValues(List<Integer> barChartNegativeValues, List<Integer> barChartPositiveValues, List<ValueLabel> valueLabels, List<Integer> intValues, List<String> stringValues, List<ValueLabel> allPositiveValues, List<ValueLabel> allNegativeValues) {
        this.barChartNegativeValues = barChartNegativeValues;
        this.barChartPositiveValues = barChartPositiveValues;
        this.valueLabels = valueLabels;
        this.intValues = intValues;
        this.stringValues = stringValues;
        this.allPositiveValues = allPositiveValues;
        this.allNegativeValues = allNegativeValues;
    }
    
    public List<Integer> getBarChartNegativeValues() {
        return barChartNegativeValues;
    }
    
    public void setBarChartNegativeValues(List<Integer> barChartNegativeValues) {
        this.barChartNegativeValues = barChartNegativeValues;
    }
    
    public List<Integer> getBarChartPositiveValues() {
        return barChartPositiveValues;
    }
    
    public void setBarChartPositiveValues(List<Integer> barChartPositiveValues) {
        this.barChartPositiveValues = barChartPositiveValues;
    }
    
    public List<ValueLabel> getValueLabels() {
        return valueLabels;
    }
    
    public void setValueLabels(List<ValueLabel> valueLabels) {
        this.valueLabels = valueLabels;
    }

    public List<Integer> getIntValues() {
        return intValues;
    }

    public void setIntValues(List<Integer> intValues) {
        this.intValues = intValues;
    }

    public List<String> getStringValues() {
        return stringValues;
    }

    public void setStringValues(List<String> stringValues) {
        this.stringValues = stringValues;
    }
    
    public List<ValueLabel> getAllPositiveValues() {
        return allPositiveValues;
    }
    
    public void setAllPositiveValues(List<ValueLabel> allPositiveValues) {
        this.allPositiveValues = allPositiveValues;
    }
    
    public List<ValueLabel> getAllNegativeValues() {
        return allNegativeValues;
    }
    
    public void setAllNegativeValues(List<ValueLabel> allNegativeValues) {
        this.allNegativeValues = allNegativeValues;
    }

    @Override
    public String toString() {
        return "ChartValues{" +
                "barChartNegativeValues=" + barChartNegativeValues +
                ", barChartPositiveValues=" + barChartPositiveValues +
                ", valueLabels=" + valueLabels +
                ", intValues=" + intValues +
                ", stringValues=" + stringValues +
                ", allPositiveValues=" + allPositiveValues +
                ", allNegativeValues=" + allNegativeValues +
                '}';
    }
}
