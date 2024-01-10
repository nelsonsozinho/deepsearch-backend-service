package com.axreng.backend.model;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;


public class Task implements Serializable {

    private final UUID id;
    private CompletableFuture<Void> process;

    private final String searchTerm;


    public Task(final UUID id, final String searchTerm) {
        this.id = id;
        this.searchTerm = searchTerm;
    }

    public UUID getId() {
        return id;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public CompletableFuture<Void> getProcess() {
        return process;
    }

    public void setProcess(CompletableFuture<Void> process) {
        this.process = process;
    }

}
