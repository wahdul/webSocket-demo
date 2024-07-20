package com.teguh.webSocketdemo.controller;

import com.teguh.webSocketdemo.persistance.model.Job;
import com.teguh.webSocketdemo.persistance.model.Worker;
import com.teguh.webSocketdemo.service.JobService;
import com.teguh.webSocketdemo.service.OptionDataService;
import com.teguh.webSocketdemo.service.WebSocketService;
import com.teguh.webSocketdemo.service.WorkerService;
import com.teguh.webSocketdemo.persistance.model.Status;
import com.teguh.webSocketdemo.persistance.model.TransportType;
import com.teguh.webSocketdemo.persistance.model.Unit;
import com.teguh.webSocketdemo.util.dto.DataTableResponseDTO;
import com.teguh.webSocketdemo.util.dto.JobRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class JobController {

    @Autowired
    private JobService jobService;

    @Autowired
    private WorkerService workerService;

    @Autowired
    private OptionDataService optionDataService;

    @Autowired
    private WebSocketService webSocketService;

    @GetMapping("/jobs")
    public String jobList(Model model) {
        return "job-list";
    }

    @GetMapping("/api/jobData")
    @ResponseBody
    public DataTableResponseDTO<Job> getAllJob(@RequestParam int draw,
                               @RequestParam int start,
                               @RequestParam int length,
                               @RequestParam Map<String, String> queryMap) {
        String searchValue = queryMap.get("search[value]");

        // Calculate page number
        int pageNumber = start / length;

        // Create Pageable instance for pagination and sorting
        Pageable pageable = PageRequest.of(pageNumber, length);

        // Fetch data from database
        Page<Job> jobPage;
        if (!searchValue.isEmpty()) {
            jobPage = jobService.findBySearchTerm(searchValue, pageable);
        } else {
            jobPage = jobService.getAllJob(pageable);
        }

        // Prepare response
        DataTableResponseDTO<Job> dtResponse = new DataTableResponseDTO<>();
        dtResponse.setDraw(draw);
        dtResponse.setRecordsTotal(jobService.countAll());
        dtResponse.setRecordsFiltered((int) jobPage.getTotalElements());
        dtResponse.setData(jobPage.getContent());

        return dtResponse;
    }

    @GetMapping("/api/workerData")
    @ResponseBody
    public List<Worker> getAllWorker() {
        return workerService.getAllWorker();
    }

    @PostMapping("/api/jobData")
    @ResponseBody
    public Job saveJob(@RequestBody JobRequestDTO requestDTO) {
        Job job = requestDTO.getJob();
        jobService.saveJob(job);
        webSocketService.sendMessage("/topic/job", requestDTO.getSocketId());
        return job;
    }

    @DeleteMapping("/api/jobData")
    @ResponseBody
    public void deleteJob(@RequestBody JobRequestDTO requestDTO) {
        Job job = requestDTO.getJob();
        job.setDeleted(new Date());//Soft Delete
        jobService.saveJob(job);
        webSocketService.sendMessage("/topic/job", requestDTO.getSocketId());
    }

    @PostMapping("/api/reorderJobData")
    @ResponseBody
    public void reorderJobData(@RequestBody JobRequestDTO requestDTO) {
        jobService.reorderJobData(requestDTO.getJobList());
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
}
