package com.axreng.backend.controller;

import com.axreng.backend.exception.TaskNotFoundException;
import com.axreng.backend.model.Task;
import com.axreng.backend.rest.ResponseTask;
import com.axreng.backend.service.ProcessService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import java.util.Objects;
import java.util.UUID;

public class TaskController {

    private final ProcessService service;

    public TaskController() {
        this.service = new ProcessService();
    }

    public void getTask(final Request request, final Response response) throws TaskNotFoundException {
        final UUID id = UUID.fromString(request.params(":id"));
        final Task task = service.findTask(id);

        response.type("application/json");

        if(Objects.nonNull(task)) {
            ResponseTask responseTask = new ResponseTask(
                    task.getId().toString(),
                    task.getUrlVisited(),
                    task.getProcess().isDone() ? "done" : "inactive");

            response.body(new Gson().toJson(responseTask));
        }

        throw new TaskNotFoundException("Task with id " + id.toString() + " was not found");
    }

}
