package com.teguh.webSocketdemo.persistance.repo;

import com.teguh.webSocketdemo.persistance.model.Allocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AllocationRepository extends JpaRepository<Allocation, Long> {
    List<Allocation> findByDateBetween(LocalDate fromDate, LocalDate toDate);
}