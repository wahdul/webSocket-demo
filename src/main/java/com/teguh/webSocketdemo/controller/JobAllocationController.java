package com.teguh.webSocketdemo.controller;

import com.teguh.webSocketdemo.persistance.model.Allocation;
import com.teguh.webSocketdemo.persistance.model.Job;
import com.teguh.webSocketdemo.persistance.model.Worker;
import com.teguh.webSocketdemo.service.AllocationService;
import com.teguh.webSocketdemo.service.JobService;
import com.teguh.webSocketdemo.service.OptionDataService;
import com.teguh.webSocketdemo.service.WebSocketService;
import com.teguh.webSocketdemo.service.WorkerService;
import com.teguh.webSocketdemo.persistance.model.Status;
import com.teguh.webSocketdemo.persistance.model.TransportType;
import com.teguh.webSocketdemo.persistance.model.Unit;
import com.teguh.webSocketdemo.util.dto.DataTableResponseDTO;
import com.teguh.webSocketdemo.util.dto.RequestDTO;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;

@Controller
public class JobAllocationController {

    @Autowired
    private JobService jobService;

    @Autowired
    private WorkerService workerService;

    @Autowired
    private OptionDataService optionDataService;

    @Autowired
    private AllocationService allocationService;

    @Autowired
    private WebSocketService webSocketService;

    @GetMapping("/jobs")
    public String jobList(Model model) {
        return "job-list";
    }

    private ModelMapper modelMapper = new ModelMapper();

    public JobAllocationController() {
        modelMapper = new ModelMapper();

        // Configure mappings
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT); // Optional: set matching strategy

        // Configure the conversion from String to Date
        modelMapper.addConverter(stringToLocalDateConverter);
    }

    @GetMapping("/api/jobData")
    @ResponseBody
    public DataTableResponseDTO<Job> getAllJob(@RequestParam int draw,
                                               @RequestParam int start,
                                               @RequestParam int length,
                                               @RequestParam Map<String, String> queryMap) {
        Page<Job> jobPage = jobService.findData(start, length, queryMap);

        // Prepare response
        DataTableResponseDTO<Job> dtResponse = new DataTableResponseDTO<>();
        dtResponse.setDraw(draw);
        dtResponse.setRecordsTotal(jobService.countAll());
        dtResponse.setRecordsFiltered((int) jobPage.getTotalElements());
        dtResponse.setData(jobPage.getContent());

        return dtResponse;
    }

    @GetMapping("/api/jobData/newData")
    @ResponseBody
    public DataTableResponseDTO<Job> getAllJobWithNewData(@RequestParam int draw,
                                                          @RequestParam int start,
                                                          @RequestParam int length,
                                                          @RequestParam Map<String, String> queryMap) {
        DataTableResponseDTO<Job> jobDataTableResponseDTO = getAllJob(draw, start, length, queryMap);

        List<Job> jobList = new ArrayList<>();
        jobList.add(new Job());
        jobList.addAll(jobDataTableResponseDTO.getData());
        jobDataTableResponseDTO.setData(jobList);

        return jobDataTableResponseDTO;
    }

    @GetMapping("/api/workerData")
    @ResponseBody
    public List<Worker> getAllWorker() {
        return workerService.getAllWorker();
    }

    @PostMapping("/api/jobData")
    @ResponseBody
    public Job saveJob(@RequestBody RequestDTO requestDTO) {
        Job job = modelMapper.map(requestDTO.getData(), Job.class);
        jobService.saveJob(job);
        webSocketService.sendMessage("/topic/job", requestDTO.getSocketId());
        return job;
    }

    @DeleteMapping("/api/jobData")
    @ResponseBody
    public void deleteJob(@RequestBody RequestDTO requestDTO) {
        Job job = modelMapper.map(requestDTO.getData(), Job.class);
        job.setDeleted(new Date());//Soft Delete
        jobService.saveJob(job);
        webSocketService.sendMessage("/topic/job", requestDTO.getSocketId());
    }

    @PostMapping("/api/reorderJobData")
    @ResponseBody
    public void reorderJobData(@RequestBody RequestDTO requestDTO) {
        jobService.reorderJobData(requestDTO.getDataList());
        webSocketService.sendMessage("/topic/job", requestDTO.getSocketId());
    }

    @GetMapping("/api/transportTypes")
    @ResponseBody
    public List<TransportType> getTransportTypes() {
        return optionDataService.getTransportTypes(); // Return all enum values as an array
    }

    @GetMapping("/api/units")
    @ResponseBody
    public List<Unit> getUnits() {
        return optionDataService.getUnits(); // Return all enum values as an array
    }

    @GetMapping("/api/statuses")
    @ResponseBody
    public List<Status> getStatuses() {
        return optionDataService.getStatuses(); // Return all enum values as an array
    }

    @GetMapping("/api/allocations")
    public ResponseEntity<Map<String, Object>> getJobs() {
        // Calculate fromDate as today
        LocalDate fromDate = LocalDate.now();

        // Calculate toDate as 7 days from today
        LocalDate toDate = fromDate.plusDays(7);

        Map<String, Object> allocationMapList = allocationService.getAllocationData(fromDate, toDate);
        return ResponseEntity.ok(allocationMapList);
    }

    @PostMapping("/api/allocationsData")
    @ResponseBody
    public List<Allocation> saveAllocation(@RequestBody RequestDTO requestDTO) {
        List<Allocation> allocationList = new ArrayList<>();
        for (Object obj : requestDTO.getDataList()) {
            allocationList.add(modelMapper.map(obj, Allocation.class));
        }
        allocationService.saveAllocation(allocationList);
        webSocketService.sendMessage("/topic/job", requestDTO.getSocketId());
        return allocationList;
    }

    @DeleteMapping("/api/allocationsData")
    @ResponseBody
    public Allocation deleteAllocation(@RequestBody RequestDTO requestDTO) {
        Allocation allocation = modelMapper.map(requestDTO.getData(), Allocation.class);
        allocationService.deleteAllocation(allocation);
        webSocketService.sendMessage("/topic/job", requestDTO.getSocketId());
        return allocation;
    }

    // Converter from String to LocalDate
    Converter<String, LocalDate> stringToLocalDateConverter = new AbstractConverter<String, LocalDate>() {
        @Override
        protected LocalDate convert(String source) {
            // Implement custom LocalDate parsing logic here
            // Example: parsing a date in ISO-8601 format (yyyy-MM-dd)
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(source, formatter);
        }
    };

}
