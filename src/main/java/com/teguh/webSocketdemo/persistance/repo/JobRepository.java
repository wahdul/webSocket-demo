package com.teguh.webSocketdemo.persistance.repo;

import com.teguh.webSocketdemo.persistance.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findAllByDeletedIsNullOrderByOrderNumber();

    @Modifying
    @Query("UPDATE Job e SET e.orderNumber = :orderNumber WHERE e.id = :id AND e.deleted IS NULL")
    void updateOrderNumber(@Param("id") Long id, @Param("orderNumber") Integer orderNumber);
}
