package com.teguh.webSocketdemo.service;

import com.teguh.webSocketdemo.persistance.model.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface JobService {
    Page<Job> getAllJob(Pageable pageable);

    void saveJob(Job job);

    void deleteJob(Job job);

    void reorderJobData(List<Job> jobs);

    Page<Job> findBySearchTerm(String searchValue, Pageable pageable);

    int countAll();
}
