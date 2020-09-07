package com.horia.reminderapi.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.horia.reminderapi.model.Responsible;
import com.horia.reminderapi.service.ReminderService;
import org.springframework.http.HttpStatus;

public class ApiResponse<T> {

    private boolean success;
    private String message;
    private HttpStatus httpStatus;

    @JsonProperty("response_content")
    private T content;

    public ApiResponse(T content, boolean success, String message, HttpStatus httpStatus) {
        this.content = content;
        this.success = success;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
