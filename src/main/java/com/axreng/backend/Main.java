package com.axreng.backend;

import com.axreng.backend.controller.TaskController;
import com.axreng.backend.exception.TaskNotFoundException;
import com.axreng.backend.service.ProcessService;

import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.post;

public class Main {

    private final ProcessService service;

    public Main(ProcessService service) {
        this.service = service;
    }

    public static void main(String[] args) {
        get("/crawl/:id", (req, res) -> {
            var controller = new TaskController();
            controller.getTask(req, res);
            return res.body();
        });

        post("/crawl", (req, res) ->
                "POST /crawl" + System.lineSeparator() + req.body());

        exception(TaskNotFoundException.class, ((exception, request, response) -> {
            response.status(404);
            response.body("");
        }));
    }
}
