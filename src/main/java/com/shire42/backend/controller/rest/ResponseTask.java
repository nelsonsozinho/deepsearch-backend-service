package com.shire42.backend.controller.rest;

import java.io.Serializable;
import java.util.List;

public class ResponseTask implements Serializable {

    private final String id;

    private final List<String> urls;

    private final String status;

    public ResponseTask(String id, List<String> urls, String status) {
        this.id = id;
        this.urls = urls;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public List<String> getUrls() {
        return urls;
    }

    public String getStatus() {
        return status;
    }
}
