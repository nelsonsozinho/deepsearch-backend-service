package com.axreng.backend.service;

import com.axreng.backend.database.EventDatabase;
import com.axreng.backend.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class ProcessService {

    private final Logger log = LoggerFactory.getLogger(ProcessService.class);

    private final EventDatabase database;

    public ProcessService() {
        this.database = EventDatabase.getInstance();
    }

    public Task newEvent(final String  term) {
        log.info("Start a new task");
        final Task event = new Task(UUID.randomUUID(), term);
        createAsyncTask(event);
        this.database.add(event);
        return event;
    }

    private void createAsyncTask(final Task event) {
        final CompletableFuture<Void> action = CompletableFuture.runAsync(() -> {
            log.info("Start process");
        });
        event.setProcess(action);
    }
}
