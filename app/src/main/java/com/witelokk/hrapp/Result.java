package com.witelokk.hrapp;

public class Result<T> {
    private T data;
    private String error;

    public Result(T data) {
        this.data = data;
    }

    public Result(String error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public String getError() {
        return error;
    }

    public boolean isSuccess() {
        return error == null;
    }
}
