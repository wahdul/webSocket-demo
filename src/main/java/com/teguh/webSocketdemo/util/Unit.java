package com.teguh.webSocketdemo.util;

public enum Unit {
    PCS("Pieces"),
    KGS("Kilograms"),
    PMC("PMC"),
    AKE("AKE");

    final String label;

    Unit(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
