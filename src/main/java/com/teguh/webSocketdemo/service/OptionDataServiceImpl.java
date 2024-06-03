package com.teguh.webSocketdemo.service;

import com.teguh.webSocketdemo.persistance.model.Status;
import com.teguh.webSocketdemo.persistance.model.TransportType;
import com.teguh.webSocketdemo.persistance.model.Unit;
import com.teguh.webSocketdemo.persistance.repo.StatusRepository;
import com.teguh.webSocketdemo.persistance.repo.TransportTypeRepository;
import com.teguh.webSocketdemo.persistance.repo.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionDataServiceImpl implements OptionDataService {

    @Autowired
    private TransportTypeRepository transportTypeRepository;

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Override
    public List<TransportType> getTransportTypes() {
        return transportTypeRepository.getAllByIdIsNotNull();
    }

    @Override
    public List<Unit> getUnits() {
        return unitRepository.getAllByIdIsNotNull();
    }

    @Override
    public List<Status> getStatuses() {
        return statusRepository.getAllByIdIsNotNull();
    }

}
