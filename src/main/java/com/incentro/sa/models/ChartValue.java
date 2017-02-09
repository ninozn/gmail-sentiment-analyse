package com.incentro.sa.models;

public class ChartValue {
    private int value;
    private String label;

    public ChartValue(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "ChartValue{" +
                "value=" + value +
                ", label='" + label + '\'' +
                '}';
    }
}
