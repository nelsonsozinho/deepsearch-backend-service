package com.axreng.backend.service;

import com.axreng.backend.client.LinkCrawler;
import com.axreng.backend.config.Environment;
import com.axreng.backend.database.EventDatabase;
import com.axreng.backend.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class TaskService {

    private final Logger log = LoggerFactory.getLogger(TaskService.class);

    private final EventDatabase database;

    private final String BASE_URL;

    public TaskService() throws Exception {
        this.database = EventDatabase.getInstance();
        this.BASE_URL = Environment.DOMAIN;
    }


    public Task findTask(final UUID taskId) {
        return database.get(taskId);
    }

    public Task newTask(final String  term) {
        log.info("Start a new task");
        final Task event = new Task(UUID.randomUUID(), term);
        createAsyncTask(event);
        this.database.add(event);
        return event;
    }

    private void createAsyncTask(final Task task) {
        final CompletableFuture<Void> action = CompletableFuture.runAsync(() -> {
            final LinkCrawler urlVisitor = new LinkCrawler(task);

            log.info("Starting process");
            final String linkSearched = urlVisitor.searchTerm(BASE_URL, task.getSearchTerm());
            task.setLinkResearchFind(linkSearched);
        });

        task.setProcess(action);
    }

}
