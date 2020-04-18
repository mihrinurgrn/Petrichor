package com.project.petrichor.model;

public class MyResponse<T> {

    private String status;
    private T object;

    public MyResponse(String status, T object) {
        this.status = status;
        this.object = object;
    }
}
