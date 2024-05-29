package com.teguh.webSocketdemo.util;

public enum Status {
    JOB_CONFIRMATION("Job Confirmation"),
    ON_ROUTE("On Route"),
    ARRIVED("Arrived"),
    PICKED_UP("Picked-up"),
    DELIVERED("Delivered"),
    COMPLETE("Complete");

    String name;

    Status(String name) {
        this.name = name;
    }
}
