package com.teguh.webSocketdemo.util;

public enum Unit {
    PCS("Pieces"),
    KGS("Kilograms"),
    PMC("PMC"),
    AKE("AKE");

    String name;

    Unit(String name) {
        this.name = name;
    }
}
