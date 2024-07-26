package com.teguh.webSocketdemo.service;

import com.teguh.webSocketdemo.persistance.model.Allocation;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface AllocationService {
    Map<String, Object> getAllocationData(LocalDate fromDate, LocalDate toDate);

    void saveAllocation(List<Allocation> allocation);

    void deleteAllocation(Allocation allocation);
}
