package com.teguh.webSocketdemo.util.dto;

import com.teguh.webSocketdemo.persistance.model.Job;

import java.util.List;

public class JobRequestDTO {
    private Job job;
    private List<Job> jobList;
    private String socketId;

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public String getSocketId() {
        return socketId;
    }

    public void setSocketId(String socketId) {
        this.socketId = socketId;
    }

    public List<Job> getJobList() {
        return jobList;
    }

    public void setJobList(List<Job> jobList) {
        this.jobList = jobList;
    }
}
