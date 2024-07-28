package com.teguh.webSocketdemo.service;

import com.teguh.webSocketdemo.persistance.model.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface JobService {
    Page<Job> getAllJob(Pageable pageable);

    void saveJob(Job job);

    Page<Job> findData(int start, int length, Map<String, String> queryMap);

    void deleteJob(Job job);

    void reorderJobData(List<Job> jobs);

    int countAll();
}
