package com.teguh.webSocketdemo.util;

public enum Status {
    JOB_CONFIRMATION("Job Confirmation"),
    ON_ROUTE("On Route"),
    ARRIVED("Arrived"),
    PICKED_UP("Picked-up"),
    DELIVERED("Delivered"),
    COMPLETE("Complete");

    final String label;

    Status(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
