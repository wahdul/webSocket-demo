package com.teguh.webSocketdemo.service;

import com.teguh.webSocketdemo.persistance.model.Allocation;

import java.time.LocalDate;
import java.util.Map;

public interface AllocationService {
    Map<String, Object> getAllocationData(LocalDate fromDate, LocalDate toDate);

    void saveAllocation(Allocation allocation);
}
