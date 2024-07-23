package com.teguh.webSocketdemo.util.dto;

import com.teguh.webSocketdemo.persistance.model.Job;

import java.util.List;

public class RequestDTO<T> {
    private T data;
    private List<T> dataList;
    private String socketId;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public String getSocketId() {
        return socketId;
    }

    public void setSocketId(String socketId) {
        this.socketId = socketId;
    }
}
