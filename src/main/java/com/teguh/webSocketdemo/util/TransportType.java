package com.teguh.webSocketdemo.util;

public enum TransportType {
    PICKUP("Pick-up cargo"),
    AIR_LODGMENT("Air Lodgment"),
    AIR_PICKUP_LODGE("Air Pick-up and Lodge (express clients)"),
    PERISHABLES_LODGMENT("Perishables Lodgment"),
    IMPORT_TERMINAL_PICKUP("Import Terminal Pick-up"),
    FBA_DELIVERY("FBA Delivery"),
    INTERSTATE_DELIVERY("Interstate delivery"),
    LOCAL_DELIVERY("Local delivery"),
    OTHER("Other");
    final String label;

    TransportType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}