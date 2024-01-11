package com.axreng.backend.controller;

import com.axreng.backend.exception.TaskNotFoundException;
import com.axreng.backend.model.Task;
import com.axreng.backend.rest.RequestTask;
import com.axreng.backend.rest.ResponseTask;
import com.axreng.backend.service.ProcessService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import java.util.Objects;
import java.util.UUID;

public class TaskController {

    private final ProcessService service;

    private final Gson gson;

    public TaskController() {
        this.service = new ProcessService();
        this.gson = new Gson();
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

            response.body(gson.toJson(responseTask));
        }

        throw new TaskNotFoundException("Task with id " + id.toString() + " was not found");
    }

    public void postTask(final Request request, final Response response) {
        final RequestTask requestTask = gson.fromJson(request.body(), RequestTask.class);
        final Task task = service.newTask(requestTask.getKeyword());

        response.type("application/json");
        response.body("{ \"id\" : \"" + task.getId() +  "\" }");
    }

}
