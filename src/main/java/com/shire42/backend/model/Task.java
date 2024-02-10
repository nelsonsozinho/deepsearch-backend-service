package com.shire42.backend.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;


public class Task implements Entity {

    private final String id;

    private final String searchTerm;

    private final List<String> linkResearchFind;

    private final List<String> urlVisited;

    private CompletableFuture<Void> process;



    public Task(final String id, final String searchTerm) {
        this.id = id;
        this.searchTerm = searchTerm;
        this.urlVisited = new ArrayList<>();
        this.linkResearchFind = new ArrayList<>();
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

    public void addUrlVisited(final String url) {
        this.urlVisited.add(url);
    }

    public List<String> getLinkResearchFind() {
        return linkResearchFind;
    }

    public void addLinkResearchFind(String linkResearchFind) {
        this.linkResearchFind.add(linkResearchFind);
    }
}
