package com.axreng.backend.controller;

import com.axreng.backend.controller.exception.IdValidateException;
import com.axreng.backend.controller.exception.SeartchTermException;
import com.axreng.backend.controller.exception.TaskNotFoundException;
import com.axreng.backend.model.Task;
import com.axreng.backend.controller.rest.RequestTask;
import com.axreng.backend.controller.rest.ResponseTask;
import com.axreng.backend.service.TaskService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import java.util.Objects;
import java.util.UUID;

public class TaskController {

    private final TaskService service;

    private final Gson gson;

    public TaskController() throws Exception {
        this.service = new TaskService();
        this.gson = new Gson();
    }

    public void getTask(final Request request, final Response response) throws TaskNotFoundException, IdValidateException {
        final String id = request.params(":id");
        validateIdSearch(id);
        final Task task = service.findTask(id);

        response.type("application/json");

        if(Objects.nonNull(task)) {
            ResponseTask responseTask = new ResponseTask(
                    task.getId(),
                    task.getUrlVisited(),
                    task.getProcess().isDone() ? "done" : "active");

            response.body(gson.toJson(responseTask));
            return;
        }

        throw new TaskNotFoundException("Task with id " + id + " not found");
    }

    public void postTask(final Request request, final Response response) throws SeartchTermException {
        final RequestTask requestTask = gson.fromJson(request.body(), RequestTask.class);

        validateSearchTerm(requestTask.getKeyword());

        final Task task = service.newTask(requestTask.getKeyword());

        response.type("application/json");
        response.body("{ \"id\" : \"" + task.getId() +  "\" }");
    }

    private void validateSearchTerm(final String term) throws SeartchTermException {
        if(!(Objects.nonNull(term) && term.length() >= 4 && term.length() <= 32)) {
            throw new SeartchTermException("Term " + term + " does is not valid");
        }
    }

    private void validateIdSearch(final String id) throws IdValidateException {
        if( !(Objects.nonNull(id) && id.length() == 8 ) ) {
            throw new IdValidateException("Id " + id + " does not an ID value");
        }
    }

}
