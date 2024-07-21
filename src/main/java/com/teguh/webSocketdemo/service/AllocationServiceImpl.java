package com.teguh.webSocketdemo.service;

import com.teguh.webSocketdemo.persistance.model.Allocation;
import com.teguh.webSocketdemo.persistance.model.Worker;
import com.teguh.webSocketdemo.persistance.repo.AllocationRepository;
import com.teguh.webSocketdemo.persistance.repo.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AllocationServiceImpl implements AllocationService {
    @Autowired
    private AllocationRepository allocationRepository;

    @Autowired
    private WorkerRepository workerRepository;

    @Override
    public Map<String, Object> getAllocationData(LocalDate fromDate, LocalDate toDate) {
        List<Allocation> allocations = allocationRepository.findByDateBetween(fromDate, toDate);
        List<Worker> workers = workerRepository.findAll();

        // Create a map to store allocation data per worker
        Map<Long, Map<String, List<Map<String, String>>>> workerAllocations = new HashMap<>();

        // Initialize worker allocations map with empty columns for each worker
        for (Worker worker : workers) {
            workerAllocations.put(worker.getId(), new HashMap<>());
        }

        // Populate worker allocations map with job references for each date
        for (Allocation allocation : allocations) {
            Long workerId = allocation.getWorker().getId();
            String jobReference = allocation.getJob().getReferenceNo();
            String dateKey = allocation.getDate().toString();

            // Retrieve or create the list of references for the specific date
            List<Map<String, String>> references = workerAllocations.get(workerId).getOrDefault(dateKey, new ArrayList<>());
            Map<String, String> referenceMap = new HashMap<>();
            referenceMap.put("reference", jobReference);
            references.add(referenceMap);

            workerAllocations.get(workerId).put(dateKey, references);
        }

        Map<String, Object> result = new HashMap<>();
        List<Map<String, String>> columns = new ArrayList<>();
        Map<String, String> column = new HashMap<>();
        column.put("title", "Worker Name");
        column.put("data", "name");
        columns.add(column);
        // Iterate through each date in the range fromDate to toDate
        LocalDate currentDateColumn = fromDate;
        while (!currentDateColumn.isAfter(toDate)) {
            Map<String, String> columnDate = new HashMap<>();
            String currentDateString = currentDateColumn.toString();
            columnDate.put("title", currentDateString);
            columnDate.put("data", currentDateString);
            columns.add(columnDate);

            // Move to the next date
            currentDateColumn = currentDateColumn.plusDays(1);
        }

        // Prepare final list with JSON format
        List<Map<String, Object>> data = new ArrayList<>();
        for (Worker worker : workers) {
            Map<String, Object> row = new HashMap<>();
            row.put("id", worker.getId());
            row.put("name", worker.getName());

            // Iterate through each date in the range fromDate to toDate
            LocalDate currentDate = fromDate;
            while (!currentDate.isAfter(toDate)) {
                String currentDateString = currentDate.toString();

                // Add dynamic columns based on worker allocations
                List<Map<String, String>> workerData = workerAllocations.get(worker.getId()).get(currentDateString);
                row.put(currentDateString, workerData);

                // Move to the next date
                currentDate = currentDate.plusDays(1);
            }

            data.add(row);
        }
        result.put("data", data);
        result.put("columns", columns);

        return result;
    }
}
