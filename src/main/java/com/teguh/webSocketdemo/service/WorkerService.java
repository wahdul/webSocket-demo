package com.teguh.webSocketdemo.service;

import com.teguh.webSocketdemo.persistance.model.Worker;

import java.util.List;

public interface WorkerService {
    List<Worker> getAllWorker();

    void saveWorker(Worker Worker);

    void deleteWorker(Worker Worker);
}
