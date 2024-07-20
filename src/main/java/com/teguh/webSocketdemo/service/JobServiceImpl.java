package com.teguh.webSocketdemo.service;

import com.teguh.webSocketdemo.persistance.model.Job;
import com.teguh.webSocketdemo.persistance.repo.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;

    @Override
    public Page<Job> getAllJob(Pageable pageable) {
        return jobRepository.findAllByDeletedIsNullOrderByOrderNumber(pageable);
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
    public Page<Job> findBySearchTerm(String searchValue, Pageable pageable) {
        return jobRepository.findByClientCodeContainingIgnoreCaseOrReferenceNoContainingIgnoreCaseOrPickupAddressContainingIgnoreCaseOrDropOffAddressContainingIgnoreCaseAndDeletedIsNullOrderByOrderNumber(searchValue, searchValue, searchValue, searchValue, pageable);
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
