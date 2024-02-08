package com.axreng.backend.service;

import com.axreng.backend.engine.config.Environment;
import com.axreng.backend.engine.crawler.LinkCrawler;
import com.axreng.backend.engine.database.EventRepositoryConcrete;
import com.axreng.backend.model.Task;
import com.axreng.backend.utils.IdUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

public class TaskService {

    private final Logger log = LoggerFactory.getLogger(TaskService.class);

    private final EventRepositoryConcrete database;

    private final String BASE_URL;

    public TaskService() throws Exception {
        this.database = EventRepositoryConcrete.getInstance();
        this.BASE_URL = Environment.DOMAIN;
    }


    public Task findTask(final String taskId) {
        return database.findById(taskId);
    }

    public Task newTask(final String  term) {
        log.info("Start a new task");
        final Task event = new Task(IdUtils.generateId(), term);
        createAsyncTask(event);
        this.database.add(event);
        return event;
    }

    private void createAsyncTask(final Task task) {
        final CompletableFuture<Void> action = CompletableFuture.runAsync(() -> {
            final LinkCrawler urlVisitor = new LinkCrawler(task);

            log.info("Starting process");
            urlVisitor.searchTerm(BASE_URL, task.getSearchTerm());
        });

        task.setProcess(action);
    }

}
