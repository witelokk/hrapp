package com.witelokk.hrapp;

public class Result<T> {
    private T data;
    private Error error;

    private Result() {
    }

    public static Result<Void> success() {
        return new Result<>();
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.data = data;
        return result;
    }

    public static <T> Result<T> error(Error error) {
        Result<T> result = new Result<>();
        result.error = error;
        return result;
    }

    public T getData() {
        return data;
    }

    public Error getError() {
        return error;
    }

    public boolean isSuccess() {
        return error == null;
    }
}
