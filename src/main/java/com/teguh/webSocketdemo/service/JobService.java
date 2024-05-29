package com.teguh.webSocketdemo.service;

import com.teguh.webSocketdemo.persistance.model.Job;

import java.util.List;

public interface JobService {
    List<Job> getAllJob();

    void saveJob(Job job);

    void deleteJob(Job job);

    void reorderJobData(List<Job> jobs);
}
