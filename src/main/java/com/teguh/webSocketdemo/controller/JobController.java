package com.teguh.webSocketdemo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.teguh.webSocketdemo.persistance.model.Job;
import com.teguh.webSocketdemo.persistance.model.Worker;
import com.teguh.webSocketdemo.service.JobService;
import com.teguh.webSocketdemo.service.WebSocketService;
import com.teguh.webSocketdemo.service.WorkerService;
import com.teguh.webSocketdemo.util.dto.JobRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
public class JobController {

    @Autowired
    private JobService jobService;

    @Autowired
    private WorkerService workerService;

    @Autowired
    private WebSocketService webSocketService;

    @GetMapping("/jobs")
    public String jobList() {
        return "job-list";
    }

    @GetMapping("/api/jobData")
    @ResponseBody
    public String getAllJob() {
        List<Job> jobList = jobService.getAllJob();
        Gson gson = new Gson();
        return gson.toJson(jobList);
    }

    @GetMapping("/api/workerData")
    @ResponseBody
    public String getAllWorker() {
        List<Worker> workerList = workerService.getAllWorker();
        Gson gson = new Gson();
        return gson.toJson(workerList);
    }

    @PostMapping("/api/jobData")
    @ResponseBody
    public void saveJob(@RequestBody JobRequestDTO requestDTO) {
        jobService.saveJob(requestDTO.getJob());
        webSocketService.sendMessage("/topic/job", requestDTO.getSocketId());
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
}
