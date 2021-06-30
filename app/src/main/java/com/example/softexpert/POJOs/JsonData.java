package com.example.softexpert.POJOs;

import java.util.List;

public class JsonData {

    private int status;
    private List<Car> data;

    public int getStatus() {
        return status;
    }

    public List<Car> getData() {
        return data;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setData(List<Car> data) {
        this.data = data;
    }
}
