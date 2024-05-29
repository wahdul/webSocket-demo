package com.teguh.webSocketdemo.persistance.repo;

import com.teguh.webSocketdemo.persistance.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRepository extends JpaRepository<Worker, Long> {
}
