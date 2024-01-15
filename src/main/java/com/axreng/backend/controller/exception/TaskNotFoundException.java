package com.axreng.backend.controller.exception;

public class TaskNotFoundException extends ResponseException {

    public TaskNotFoundException(String message) {
        super(message);
    }
}
