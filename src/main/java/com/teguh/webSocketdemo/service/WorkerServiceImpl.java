package com.teguh.webSocketdemo.service;

import com.teguh.webSocketdemo.persistance.model.Worker;
import com.teguh.webSocketdemo.persistance.repo.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkerServiceImpl implements WorkerService {

    @Autowired
    private WorkerRepository WorkerRepository;

    @Override
    public List<Worker> getAllWorker() {
        return WorkerRepository.findAll();
    }

    @Override
    public void saveWorker(Worker worker) {
        WorkerRepository.save(worker);
    }

    @Override
    public void deleteWorker(Worker worker) {
        WorkerRepository.delete(worker);
    }
}
