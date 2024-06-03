package com.teguh.webSocketdemo.persistance.repo;

import com.teguh.webSocketdemo.persistance.model.TransportType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransportTypeRepository extends JpaRepository<TransportType, Long> {
    List<TransportType> getAllByIdIsNotNull();
}
