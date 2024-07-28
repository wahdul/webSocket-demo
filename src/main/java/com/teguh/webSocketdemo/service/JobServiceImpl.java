package com.teguh.webSocketdemo.service;

import com.teguh.webSocketdemo.persistance.model.Job;
import com.teguh.webSocketdemo.persistance.repo.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;

    @Override
    public Page<Job> getAllJob(Pageable pageable) {
        return jobRepository.findAllByDeletedIsNull(pageable);
    }

    @Override
    public void saveJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    @Transactional
    public void reorderJobData(List<Job> jobs) {
        for (Job job : jobs) {
            jobRepository.updateOrderNumber(job.getId(), job.getOrderNumber());
        }
    }

    @Override
    public Page<Job> findData(int start, int length, Map<String, String> queryMap) {
        // Calculate page number
        int pageNumber = start / length;

        // Create Pageable instance for pagination and sorting
        Pageable pageable = PageRequest.of(pageNumber, length);

        if (queryMap.containsKey("order[0][column]") && queryMap.containsKey("order[0][dir]")) {
            String sortBy = queryMap.get("columns[" + queryMap.get("order[0][column]") + "][data]");
            if ("unit".equals(sortBy)) {
                sortBy = "unit.label";
            }
            if ("transportType".equals(sortBy)) {
                sortBy = "transportType.label";
            }
            if ("status".equals(sortBy)) {
                sortBy = "status.label";
            }

            String sortDir = queryMap.get("order[0][dir]");

            Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);

            // Create Pageable instance for pagination and sorting
            pageable = PageRequest.of(pageNumber, length, sort);
        }

        // Fetch data from database
        String searchValue = queryMap.get("search[value]");
        if (!searchValue.isEmpty()) {
            //Create probe object
            Job probe = new Job(searchValue);
            // Create an ExampleMatcher to specify matching conditions
            ExampleMatcher matcher = ExampleMatcher.matchingAny()
                    .withIgnoreCase()                // Ignore case for string fields
                    .withMatcher("deleted", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING); // Partial match for strings

            // Create an Example instance
            Example<Job> example = Example.of(probe, matcher);

            return jobRepository.findAll(example, pageable);
        } else {
            //Create probe object
            Job probe = new Job();
            boolean isSearch = false;
            for (Map.Entry<String, String> entry : queryMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (key.endsWith("[search][value]") && !value.isEmpty()) {
                    String data = queryMap.get(key.replace("[search][value]", "[data]"));
                    try {
                        String setterName = "set" + Character.toUpperCase(data.charAt(0)) + data.substring(1);
                        Method setter = probe.getClass().getMethod(setterName, value.getClass());
                        setter.invoke(probe, value);
                        isSearch = true;
                    } catch (Exception e) {
                        e.printStackTrace(); // Handle the exceptions as needed
                    }
                }
            }
            if (isSearch) {
                // Create an ExampleMatcher to specify matching conditions
                ExampleMatcher matcher = ExampleMatcher.matching()
                        .withIgnoreCase()                // Ignore case for string fields
                        .withMatcher("deleted", ExampleMatcher.GenericPropertyMatchers.exact())
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING); // Partial match for strings

                // Create an Example instance
                Example<Job> example = Example.of(probe, matcher);

                return jobRepository.findAll(example, pageable);
            }
            return getAllJob(pageable);
        }

    }

    @Override
    public void deleteJob(Job job) {
        jobRepository.delete(job);
    }

    @Override
    public int countAll() {
        return jobRepository.countAllByDeletedIsNull();
    }
}
