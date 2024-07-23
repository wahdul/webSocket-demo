package com.teguh.webSocketdemo.service;

import com.teguh.webSocketdemo.persistance.model.Allocation;
import com.teguh.webSocketdemo.persistance.model.Worker;
import com.teguh.webSocketdemo.persistance.repo.AllocationRepository;
import com.teguh.webSocketdemo.persistance.repo.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class AllocationServiceImpl implements AllocationService {
    @Autowired
    private AllocationRepository allocationRepository;

    @Autowired
    private WorkerRepository workerRepository;

    @Override
    public Map<String, Object> getAllocationData(LocalDate fromDate, LocalDate toDate) {
        List<Allocation> allocations = allocationRepository.findByDateBetweenOrderByIndexAsc(fromDate, toDate);
        List<Worker> workers = workerRepository.findAll();

        // Create a map to store allocation data per worker
        Map<Long, Map<String, List<Map<String, Object>>>> workerAllocations = new HashMap<>();

        // Initialize worker allocations map with empty columns for each worker
        for (Worker worker : workers) {
            workerAllocations.put(worker.getId(), new HashMap<>());
        }

        // Populate worker allocations map with job references for each date
        for (Allocation allocation : allocations) {
            Long workerId = allocation.getWorker().getId();
            String dateKey = allocation.getDate().toString();

            // Retrieve or create the list of references for the specific date
            List<Map<String, Object>> allocationList = workerAllocations.get(workerId).getOrDefault(dateKey, new ArrayList<>());
            Map<String, Object> allocationMap;
            try {
                allocationMap = entityToMap(allocation);
                allocationList.add(allocationMap);
                workerAllocations.get(workerId).put(dateKey, allocationList);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        Map<String, Object> result = new HashMap<>();
        List<Map<String, String>> columns = new ArrayList<>();
        Map<String, String> column = new HashMap<>();
        column.put("title", "Worker Name");
        column.put("data", "worker.name");
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
            Map<String, Object> workerMap;
            try {
                workerMap = entityToMap(worker);
                row.put("worker", workerMap);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            // Iterate through each date in the range fromDate to toDate
            LocalDate currentDate = fromDate;
            while (!currentDate.isAfter(toDate)) {
                String currentDateString = currentDate.toString();

                // Add dynamic columns based on worker allocations
                List<Map<String, Object>> workerData = workerAllocations.get(worker.getId()).get(currentDateString);
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

    @Override
    public void saveAllocation(List<Allocation> allocations) {
        allocationRepository.saveAll(allocations);
    }

    public static Map<String, Object> entityToMap(Object entity) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();
        Class<?> clazz = entity.getClass();

        // Get all declared fields of the class (including private ones)
        Field[] fields = clazz.getDeclaredFields();

        // Iterate through each field
        for (Field field : fields) {
            field.setAccessible(true); // Ensure private fields are accessible
            Object value = field.get(entity); // Get value of the field from the entity

            // Check if the value is another entity (e.g., Join)
            if (value != null && !isPrimitiveOrStringOrDate(value.getClass())) {
                // If it's another entity, recursively convert it to a map
                map.put(field.getName(), entityToMap(value));
            } else {
                // Otherwise, put field name and its value into the map
                map.put(field.getName(), value);
            }
        }

        return map;
    }

    // Helper method to check if a class is a primitive type or String
    // Define a set of classes to check against
    private static final Set<Class<?>> PRIMITIVE_AND_WRAPPER_TYPES = new HashSet<>();
    static {
        PRIMITIVE_AND_WRAPPER_TYPES.add(Boolean.class);
        PRIMITIVE_AND_WRAPPER_TYPES.add(Character.class);
        PRIMITIVE_AND_WRAPPER_TYPES.add(Byte.class);
        PRIMITIVE_AND_WRAPPER_TYPES.add(Short.class);
        PRIMITIVE_AND_WRAPPER_TYPES.add(Integer.class);
        PRIMITIVE_AND_WRAPPER_TYPES.add(Long.class);
        PRIMITIVE_AND_WRAPPER_TYPES.add(Float.class);
        PRIMITIVE_AND_WRAPPER_TYPES.add(Double.class);
        PRIMITIVE_AND_WRAPPER_TYPES.add(Void.class);
        PRIMITIVE_AND_WRAPPER_TYPES.add(String.class);
        PRIMITIVE_AND_WRAPPER_TYPES.add(Date.class);
        PRIMITIVE_AND_WRAPPER_TYPES.add(BigDecimal.class);
        PRIMITIVE_AND_WRAPPER_TYPES.add(BigInteger.class);
        PRIMITIVE_AND_WRAPPER_TYPES.add(LocalDate.class);
    }

    public static boolean isPrimitiveOrStringOrDate(Class<?> clazz) {
        return clazz.isPrimitive() || PRIMITIVE_AND_WRAPPER_TYPES.contains(clazz);
    }
}
