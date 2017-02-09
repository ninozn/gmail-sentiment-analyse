package com.incentro.sa.models;

import java.util.List;

/**
 * Created by Roger on 15-12-2016.
 */
public class PieChartValues {

    private List<Integer> barNValues;
    private List<Integer> barPValues;
    private List<ChartValue> chartValues;
    private List<Integer> intValues;
    private List<String> stringValues;
    private List<ChartValue> allPositiveValues;
    private List<ChartValue> allNegativeValues;

    public PieChartValues() {
    }

    public PieChartValues(List<Integer> barNValues, List<Integer> barPValues, List<ChartValue> chartValues, List<Integer> intValues, List<String> stringValues, List<ChartValue> allPositiveValues, List<ChartValue> allNegativeValues) {
        this.barNValues = barNValues;
        this.barPValues = barPValues;
        this.chartValues = chartValues;
        this.intValues = intValues;
        this.stringValues = stringValues;
        this.allPositiveValues = allPositiveValues;
        this.allNegativeValues = allNegativeValues;
    }

    public List<Integer> getBarNValues() {
        return barNValues;
    }

    public void setBarNValues(List<Integer> barNValues) {
        this.barNValues = barNValues;
    }

    public List<Integer> getBarPValues() {
        return barPValues;
    }

    public void setBarPValues(List<Integer> barPValues) {
        this.barPValues = barPValues;
    }

    public List<ChartValue> getChartValues() {
        return chartValues;
    }

    public void setChartValues(List<ChartValue> chartValues) {
        this.chartValues = chartValues;
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

    public List<ChartValue> getAllPositiveValues() {
        return allPositiveValues;
    }

    public void setAllPositiveValues(List<ChartValue> allPositiveValues) {
        this.allPositiveValues = allPositiveValues;
    }

    public List<ChartValue> getAllNegativeValues() {
        return allNegativeValues;
    }

    public void setAllNegativeValues(List<ChartValue> allNegativeValues) {
        this.allNegativeValues = allNegativeValues;
    }

    @Override
    public String toString() {
        return "PieChartValues{" +
                "barNValues=" + barNValues +
                ", barPValues=" + barPValues +
                ", chartValues=" + chartValues +
                ", intValues=" + intValues +
                ", stringValues=" + stringValues +
                ", allPositiveValues=" + allPositiveValues +
                ", allNegativeValues=" + allNegativeValues +
                '}';
    }
}
