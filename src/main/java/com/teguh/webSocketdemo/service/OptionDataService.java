package com.teguh.webSocketdemo.service;

import com.teguh.webSocketdemo.persistance.model.Status;
import com.teguh.webSocketdemo.persistance.model.TransportType;
import com.teguh.webSocketdemo.persistance.model.Unit;

import java.util.List;

public interface OptionDataService {
    List<TransportType> getTransportTypes();

    List<Unit> getUnits();

    List<Status> getStatuses();
}
