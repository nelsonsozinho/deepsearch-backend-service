package com.axreng.backend.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;


public class Task implements Serializable {

    private final String id;

    private final String searchTerm;

    private String linkResearchFind;

    private List<String> urlVisited;

    private CompletableFuture<Void> process;



    public Task(final String id, final String searchTerm) {
        this.id = id;
        this.searchTerm = searchTerm;
        this.urlVisited = new ArrayList<>();
    }

    public String getId() {
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

    public List<String> getUrlVisited() {
        return urlVisited;
    }

    public void setUrlVisited(List<String> urlVisited) {
        this.urlVisited = urlVisited;
    }

    public void addUrlVisited(final String url) {
        this.urlVisited.add(url);
    }

    public String getLinkResearchFind() {
        return linkResearchFind;
    }

    public void setLinkResearchFind(String linkResearchFind) {
        this.linkResearchFind = linkResearchFind;
    }
}
