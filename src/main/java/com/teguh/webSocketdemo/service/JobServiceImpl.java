package com.teguh.webSocketdemo.service;

import com.teguh.webSocketdemo.persistance.model.Job;
import com.teguh.webSocketdemo.persistance.repo.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;

    @Override
    public List<Job> getAllJob() {
        return jobRepository.findAllByDeletedIsNullOrderByOrderNumber();
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
    public void deleteJob(Job job) {
        jobRepository.delete(job);
    }
}
