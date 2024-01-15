package com.axreng.backend;

import com.axreng.backend.controller.TaskController;
import com.axreng.backend.controller.exception.IdValidateException;
import com.axreng.backend.controller.exception.SeartchTermException;
import com.axreng.backend.controller.exception.TaskNotFoundException;

import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.post;

public class Main {

    public static void main(String[] args) {
        get("/crawl/:id", (req, res) -> {
            var controller = new TaskController();
            controller.getTask(req, res);
            return res.body();
        });

        post("/crawl", (req, res) -> {
            var controller = new TaskController();
            controller.postTask(req, res);
            return res.body();
        });

        exception(TaskNotFoundException.class, ((exception, request, response) -> {
            response.status(404);
            response.type("application/json");
            response.body("{ \"messge\": " + "\"" + exception.getMessage() + "\"}");
        }));

        exception(SeartchTermException.class, ((exception, request, response) -> {
            response.status(400);
            response.type("application/json");
            response.body("{ \"messge\": " + "\"" + exception.getMessage() + "\"}");
        }));

        exception(IdValidateException.class, ((exception, request, response) -> {
            response.status(400);
            response.type("application/json");
            response.body("{ \"messge\": " + "\"" + exception.getMessage() + "\"}");
        }));
    }
}
