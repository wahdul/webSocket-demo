package com.teguh.webSocketdemo.persistance.repo;

import com.teguh.webSocketdemo.persistance.model.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JobRepository extends JpaRepository<Job, Long> {
    int countAllByDeletedIsNull();
    Page<Job> findAllByDeletedIsNullOrderByOrderNumber(Pageable pageable);
    Page<Job> findByClientCodeContainingIgnoreCaseOrReferenceNoContainingIgnoreCaseOrPickupAddressContainingIgnoreCaseOrDropOffAddressContainingIgnoreCaseAndDeletedIsNullOrderByOrderNumber(String cc, String ref, String pa, String da, Pageable pageable);

    @Modifying
    @Query("UPDATE Job e SET e.orderNumber = :orderNumber WHERE e.id = :id AND e.deleted IS NULL")
    void updateOrderNumber(@Param("id") Long id, @Param("orderNumber") Integer orderNumber);
}
