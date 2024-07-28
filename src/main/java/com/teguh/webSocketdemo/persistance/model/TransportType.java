package com.teguh.webSocketdemo.persistance.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class TransportType {
    @Id
    private Long id;
    private String label;

    public TransportType(Long valueOf) {
        this.id = valueOf;
    }

    public TransportType() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}