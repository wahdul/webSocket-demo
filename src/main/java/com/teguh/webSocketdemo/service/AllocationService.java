package com.teguh.webSocketdemo.service;

import java.time.LocalDate;
import java.util.Map;

public interface AllocationService {
    Map<String, Object> getAllocationData(LocalDate fromDate, LocalDate toDate);
}
