package com.axreng.backend.exception;

public class TaskNotFoundException extends ResponseException {

    public TaskNotFoundException(String message) {
        super(message);
    }
}
