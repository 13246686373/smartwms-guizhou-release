package com.frdscm.wms.entity.bo.client;

import org.springframework.http.HttpStatus;

/**
 * Created by maxuan on 15/3/2018.
 */
public class Response<T> {

    private HttpStatus status;
    private String message;
    private T data;
    public Response(HttpStatus status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Integer getStatus() {
        return status.value();
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
