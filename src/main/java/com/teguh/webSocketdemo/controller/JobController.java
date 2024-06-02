package com.teguh.webSocketdemo.controller;

import com.teguh.webSocketdemo.persistance.model.Job;
import com.teguh.webSocketdemo.persistance.model.Worker;
import com.teguh.webSocketdemo.service.JobService;
import com.teguh.webSocketdemo.service.WebSocketService;
import com.teguh.webSocketdemo.service.WorkerService;
import com.teguh.webSocketdemo.util.Status;
import com.teguh.webSocketdemo.util.TransportType;
import com.teguh.webSocketdemo.util.Unit;
import com.teguh.webSocketdemo.util.dto.EnumDTO;
import com.teguh.webSocketdemo.util.dto.JobRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class JobController {

    @Autowired
    private JobService jobService;

    @Autowired
    private WorkerService workerService;

    @Autowired
    private WebSocketService webSocketService;

    @GetMapping("/jobs")
    public String jobList(Model model) {
        model.addAttribute("transportTypeMap", Arrays.stream(TransportType.values()).collect(Collectors.toMap(TransportType::name, TransportType::getLabel)));
        model.addAttribute("unitMap", Arrays.stream(Unit.values()).collect(Collectors.toMap(Unit::name, Unit::getLabel)));
        model.addAttribute("statusMap", Arrays.stream(Status.values()).collect(Collectors.toMap(Status::name, Status::getLabel)));
        return "job-list";
    }

    @GetMapping("/api/jobData")
    @ResponseBody
    public List<Job> getAllJob() {
        return jobService.getAllJob();
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
}
